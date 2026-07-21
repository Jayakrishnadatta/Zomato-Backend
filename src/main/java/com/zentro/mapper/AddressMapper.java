package com.zentro.mapper;


import com.zentro.dto.address.AddressRequest;
import com.zentro.dto.address.AddressResponse;
import com.zentro.model.Address;

public class AddressMapper {

    private AddressMapper() {
    }

    // Request → Entity

    public static Address toEntity(
            AddressRequest request
    ) {

        Address address = new Address();

        address.setFullName(
                request.getFullName()
        );

        address.setMobileNumber(
                request.getMobileNumber()
        );

        address.setHouseNumber(
                request.getHouseNumber()
        );

        address.setStreetName(
                request.getStreetName()
        );

        address.setCity(
                request.getCity()
        );

        address.setState(
                request.getState()
        );

        address.setCountry(
                request.getCountry()
        );

        address.setPostalCode(
                request.getPostalCode()
        );

        return address;
    }

    // Entity → Response

    public static AddressResponse
    toResponse(Address address) {

        AddressResponse response =
                new AddressResponse();

        response.setAddressId(
                address.getAddressId()
        );

        response.setFullName(
                address.getFullName()
        );

        response.setMobileNumber(
                address.getMobileNumber()
        );

        response.setHouseNumber(
                address.getHouseNumber()
        );

        response.setStreetName(
                address.getStreetName()
        );

        response.setCity(
                address.getCity()
        );

        response.setState(
                address.getState()
        );

        response.setCountry(
                address.getCountry()
        );

        response.setPostalCode(
                address.getPostalCode()
        );

        return response;
    }
}
