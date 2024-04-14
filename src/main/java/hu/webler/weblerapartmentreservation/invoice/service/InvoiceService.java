package hu.webler.weblerapartmentreservation.invoice.service;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceUpdateModel;
import hu.webler.weblerapartmentreservation.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.invoice.util.InvoiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final AddressRepository addressRepository;

    public List<InvoiceModel> findAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceMapper::mapInvoiceEntityToInvoiceModel)
                .collect(Collectors.toList());
    }

    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Invoice with id %d was not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }

    public InvoiceModel createInvoice(InvoiceCreateModel invoiceCreateModel) {
        Address addressToSave = null;
        if (invoiceCreateModel.getAddress() != null) {
            Optional<Address> optionalAddress = addressRepository.findAddressByCountryAndPostalCodeAndCityAndLine(
                    invoiceCreateModel.getAddress().getCountry(),
                    invoiceCreateModel.getAddress().getPostalCode(),
                    invoiceCreateModel.getAddress().getCity(),
                    invoiceCreateModel.getAddress().getLine());

            if (optionalAddress.isPresent()) {
                addressToSave = optionalAddress.get();
            } else {
                AddressCreateModel model = new AddressCreateModel();
                model.setCountry(invoiceCreateModel.getAddress().getCountry());
                model.setPostalCode(invoiceCreateModel.getAddress().getPostalCode());
                model.setCity(invoiceCreateModel.getAddress().getCity());
                model.setLine(invoiceCreateModel.getAddress().getLine()); // Ensure line is not null
                addressToSave = createAddress(model);
            }
        } else {
            throw new IllegalArgumentException("Address details are required");
        }
        invoiceCreateModel.setAddress(addressToSave);
        return InvoiceMapper.mapInvoiceEntityToInvoiceModel(invoiceRepository.save(InvoiceMapper.mapInvoiceCreateModelToInvoiceEntity(invoiceCreateModel)));
    }

    private Address createAddress(AddressCreateModel addressCreateModel) {
        Address address = new Address();
        address.setCountry(addressCreateModel.getCountry());
        address.setPostalCode(addressCreateModel.getPostalCode());
        address.setCity(addressCreateModel.getCity());

        if (addressCreateModel.getLine() != null) {
            address.setLine(addressCreateModel.getLine());
        } else {
            address.setLine("");
        }
        return addressRepository.save(address);
    }

    public void deleteInvoice(Long id) {
        findInvoiceById(id);
        invoiceRepository.deleteById(id);
    }

    public InvoiceModel updateInvoice(Long id, InvoiceUpdateModel invoiceUpdateModel) {
        Invoice invoice = findInvoiceById(id);
        if (invoice != null) {
            return InvoiceMapper.mapInvoiceEntityToInvoiceModel(invoiceRepository
                    .save(InvoiceMapper.mapInvoiceUpdateModelToInvoiceEntity(invoiceUpdateModel)));
        }
        return null;
    }
}
