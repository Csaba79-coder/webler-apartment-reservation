package hu.webler.weblerapartmentreservation.view;

import hu.webler.weblerapartmentreservation.domain.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thy")
public class InvoiceWebController {

    private final InvoiceService invoiceService;

    @GetMapping("/invoices")
    public String renderAllInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.findAllInvoices());
        return "invoice";
    }
}
