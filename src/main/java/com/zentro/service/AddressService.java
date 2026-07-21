package com.zentro.service;

import java.util.List;

import com.zentro.dto.address.AddressRequest;
import com.zentro.dto.address.AddressResponse;

public interface AddressService {

    AddressResponse addAddress(
            Long userId,
            AddressRequest dto
    );

    AddressResponse updateAddress(
            Long addressId,
            AddressRequest dto
    );

    void deleteAddress(Long addressId);

    List<AddressResponse>
    getAddressesByUserId(
            Long userId
    );
}
