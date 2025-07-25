package users.user.service.vendor_service;

import users.user.entity.Vendor;

import java.util.List;

public interface VendorDao {
    Vendor createVendor(Vendor vendor);
    Vendor updateVendor(Vendor vendor);
    List<Vendor> getAllVendors();
    List<Vendor> getVendorByVendorType(String vendorType);
    List<Vendor> getVendorBySubscriptionType(String subscription);
    Vendor getVendorById(Long id);
    void deleteVendorById(long id);
}
