package com.zentro.repository;

import com.zentro.dto.address.AddressResponse;
import com.zentro.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    @Query("select new com.zentro.dto.address.AddressResponse(a.addressId, a.fullName, a.mobileNumber, a.houseNumber, a.streetName, a.city, a.state, a.country, a.postalCode) from Address a where a.user.Id = :userId")
    List<AddressResponse> findAddressResponsesByUserId(@Param("userId") Long userId);
}
