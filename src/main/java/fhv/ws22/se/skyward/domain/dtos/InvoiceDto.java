package fhv.ws22.se.skyward.domain.dtos;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class InvoiceDto extends AbstractDto {
    private BigInteger invoiceNumber;
    private String companyName;
    private LocalDateTime invoiceDateTime;
    private Boolean isPaid;
    private AddressDto hotelAddress;
    private AddressDto customerAddress;
    private BookingDto booking;
    private CustomerDto billedCustomer;

    public InvoiceDto() {
    }
    public InvoiceDto(LocalDateTime invoiceDateTime, Boolean isPaid, AddressDto customerAddress, BookingDto booking) {
        this.invoiceDateTime = invoiceDateTime;
        this.isPaid = isPaid;
        this.customerAddress = customerAddress;
        this.booking = booking;

        this.companyName = "Skyward International";
        this.hotelAddress = new AddressDto("ExampleStreet", 2, 1234, "New York", "United States");
    }

    public BigInteger getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(BigInteger invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getInvoiceDateTime() {
        return invoiceDateTime;
    }
    public void setInvoiceDateTime(LocalDateTime invoiceDateTime) {
        this.invoiceDateTime = invoiceDateTime;
    }

    public AddressDto getHotelAddress() {
        return hotelAddress;
    }
    public void setHotelAddress(AddressDto hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public AddressDto getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(AddressDto customerAddress) {
        this.customerAddress = customerAddress;
    }

    public BookingDto getBooking() {
        return booking;
    }
    public void setBooking(BookingDto booking) {
        this.booking = booking;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public CustomerDto getBilledCustomer() {
        return billedCustomer;
    }
    public void setBilledCustomer(CustomerDto billedCustomer) {
        this.billedCustomer = billedCustomer;
    }

    @Override
    public String toString() {
        return "InvoiceDto{" +
                "invoiceNumber=" + invoiceNumber +
                ", companyName='" + companyName + '\'' +
                ", invoiceDateTime=" + invoiceDateTime +
                ", isPaid=" + isPaid +
                ", hotelAddress=" + hotelAddress +
                ", customerAddress=" + customerAddress +
                ", booking=" + booking +
                ", billedCustomer=" + billedCustomer +
                '}';
    }
}
