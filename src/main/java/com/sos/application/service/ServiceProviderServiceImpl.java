package com.sos.application.service;

import com.sos.application.entity.ServiceProvider;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.serviceProvider.request.CreateServiceProviderRequest;
import com.sos.application.model.serviceProvider.response.CreateServiceProviderResponse;
import com.sos.application.repository.ServiceProviderRepository;
import com.sos.application.service.fileUploads.FileUploadService;
import com.sos.application.validator.ServiceProviderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    @Autowired
    private ServiceProviderValidator serviceProviderValidator;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public CreateServiceProviderResponse createAccountNameAndPhoneNumber(CreateServiceProviderRequest createServiceProviderRequest) throws BadRequestBodyException {
        logger.debug("In createAccountNameAndPhoneNumber with CreateServiceProviderRequest: {}", createServiceProviderRequest);
        serviceProviderValidator.validateNameAndPhoneNumber(createServiceProviderRequest.getName(), createServiceProviderRequest.getPhoneNumber());
        checkPhoneNumberExists(createServiceProviderRequest.getPhoneNumber());

        ServiceProvider serviceProvider = createAndSaveServiceProvider(createServiceProviderRequest);
        CreateServiceProviderResponse createServiceProviderResponse = constructCreateServiceProviderResponse(serviceProvider);
        return createServiceProviderResponse;
    }

    @Override
    public void uploadAadhaar(MultipartFile aadhaarFile, Long serviceProviderId) throws BadRequestBodyException, ResourceNotExistsException, IOException {
        logger.info("Upload MultipartFile: {} for ServiceProviderId: {}", aadhaarFile, serviceProviderId);
        ServiceProvider serviceProvider = getServiceProviderById(serviceProviderId);
        isVerifiedServiceProvider(serviceProvider);

        String filePath = fileUploadService.uploadFile(aadhaarFile, serviceProviderId);
        setAadhaarPath(serviceProvider, filePath);

        serviceProvider = serviceProviderRepository.save(serviceProvider);
        logger.debug("Updated serviceProvider with aadhaar path , ServiceProvider: {}", serviceProvider);
    }

    @Override
    public byte[] getAadhaarFile(Long serviceProviderId) throws ResourceNotExistsException, IOException {
        logger.info("Get AadhaarFile for serviceProviderId: {}", serviceProviderId);
        ServiceProvider serviceProvider = getServiceProviderById(serviceProviderId);
        checkAadhaarPath(serviceProvider);

        byte[] bytes = fileUploadService.getFile(serviceProvider.getAadhaarPath());
        return bytes;
    }


    private void isVerifiedServiceProvider(ServiceProvider serviceProvider) throws BadRequestBodyException {
        logger.info("checking serviceProvider hasVerified or not");
        if(serviceProvider.getHasVerified()) {
            throw new BadRequestBodyException("Verified serviceProvider can't change there fileUploads");
        }
    }

    private void checkAadhaarPath(ServiceProvider serviceProvider) throws ResourceNotExistsException {
        logger.info("Checking for valid aadhaarPath for ServiceProvider: {}", serviceProvider);
        if(serviceProvider.getAadhaarPath() == null)
            throw new ResourceNotExistsException(String.format("No file has been uploaded by ServiceProvider"));
    }

    private void setAadhaarPath(ServiceProvider serviceProvider, String filePath) throws ResourceNotExistsException {
        logger.info("Setting aadhaarPath to ServiceProvider: {} with aadhaarPath: {}", serviceProvider, filePath);
        serviceProvider.setAadhaarPath(filePath);
    }


    private ServiceProvider getServiceProviderById(Long serviceProviderId) throws ResourceNotExistsException {
        logger.info("Fetching ServiceProvider with serviceProviderId: {}", serviceProviderId);
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(serviceProviderId);
        if(serviceProvider.isEmpty()) {
            throw new ResourceNotExistsException(String.format("ServiceProviderId: %d not exists.", serviceProviderId));
        }
        return serviceProvider.get();
    }


    private void checkPhoneNumberExists(String phoneNumber) throws BadRequestBodyException {
        logger.info("Checking if phoneNumber: {} exists or not", phoneNumber);
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.checkPhoneNumberExists(phoneNumber);
        if(serviceProvider.isPresent()) {
            throw new BadRequestBodyException(String.format("Account already exists with provided phoneNumber: %s", phoneNumber));
        }
    }

    private ServiceProvider createAndSaveServiceProvider(CreateServiceProviderRequest createServiceProviderRequest) {
        logger.info("Creating & saving ServiceProvider with CreateServiceProviderRequest: {}", createServiceProviderRequest);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(createServiceProviderRequest.getName());
        serviceProvider.setPhoneNumber(createServiceProviderRequest.getPhoneNumber());
        serviceProvider.setHasVerified(false);
        serviceProvider.setCreatedTime(LocalDateTime.now());

        ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
        logger.debug("created ServiceProvider:  {}", serviceProvider);

        return createdServiceProvider;
    }

    private CreateServiceProviderResponse constructCreateServiceProviderResponse(ServiceProvider serviceProvider) {
        logger.info("Constructing CreateServiceProviderResponse from ServiceProvider: {}", serviceProvider);
        CreateServiceProviderResponse createServiceProviderResponse = new CreateServiceProviderResponse();
        createServiceProviderResponse.setId(serviceProvider.getId());
        createServiceProviderResponse.setName(serviceProvider.getName());
        createServiceProviderResponse.setPhoneNumber(serviceProvider.getPhoneNumber());
        logger.debug("Constructed CreateServiceProviderResponse: {}", createServiceProviderResponse);
        return createServiceProviderResponse;
    }

}
