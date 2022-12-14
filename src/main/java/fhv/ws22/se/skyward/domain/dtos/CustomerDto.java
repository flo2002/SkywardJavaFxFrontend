package fhv.ws22.se.skyward.domain.dtos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("CustomerDto");
    private String firstName;
    private String lastName;
    private AddressDto address;
    private String customerType;

    public CustomerDto() {
    }
    public CustomerDto(String firstName, String lastName, AddressDto address, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.customerType = type;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressDto getAddress() {
        return address;
    }
    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String type) {
        this.customerType = type;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", type=" + customerType +
                '}';
    }
}
