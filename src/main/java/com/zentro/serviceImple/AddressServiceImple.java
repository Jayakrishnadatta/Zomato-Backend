package com.zentro.serviceImple;

import com.zentro.dto.address.AddressRequest;
import com.zentro.dto.address.AddressResponse;
import com.zentro.mapper.AddressMapper;
import com.zentro.model.Address;
import com.zentro.model.User;
import com.zentro.repository.AddressRepository;
import com.zentro.repository.UserRepository;
import com.zentro.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImple implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImple(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddressResponse addAddress(Long userId, AddressRequest dto) {
        validateUserId(userId);
        validateDto(dto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Address address = AddressMapper.toEntity(dto);
        address.setUser(user);

        return AddressMapper.toResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest dto) {
        validateAddressId(addressId);
        validateDto(dto);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        copyAddress(dto, address);

        return AddressMapper.toResponse(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Long addressId) {
        validateAddressId(addressId);
        if (!addressRepository.existsById(addressId)) {
            throw new IllegalArgumentException("Address not found");
        }
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        validateUserId(userId);
        return addressRepository.findAddressResponsesByUserId(userId);
    }

    private void copyAddress(AddressRequest dto, Address address) {
        address.setFullName(dto.getFullName());
        address.setMobileNumber(dto.getMobileNumber());
        address.setHouseNumber(dto.getHouseNumber());
        address.setStreetName(dto.getStreetName());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPostalCode(dto.getPostalCode());
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User is required");
        }
    }

    private void validateAddressId(Long addressId) {
        if (addressId == null || addressId <= 0) {
            throw new IllegalArgumentException("Address is required");
        }
    }

    private void validateDto(AddressRequest dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Address data is required");
        }
    }
}
