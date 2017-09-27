package beans.controllers;

import beans.models.Ticket;
import beans.models.User;
import beans.services.BookingService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author piotrsekula
 * @since 27.09.17.
 */
@Controller
@RequestMapping("/booking")
public class BookingController {

    private BookingService service;

    @Autowired
    public BookingController(@Qualifier(value = "bookingServiceImpl") BookingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/getPrice", method = RequestMethod.GET)
    public String getPrice(@RequestParam String event,
                           @RequestParam String auditorium,
                           @RequestParam("eventTime") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") LocalDateTime dateTime,
                           @RequestParam(value = "seats") Integer[] seats,
                           @RequestParam User user,
                           Model model) {
        model.addAttribute("price", service.getTicketPrice(event, auditorium, dateTime, Arrays.asList(seats), user));
        return "price";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String bookTicket(@RequestParam Ticket ticket,
                           @RequestParam User user,
                           Model model) {
        model.addAttribute("ticket", service.bookTicket(user, ticket));
        return "ticket";
    }

    @RequestMapping("/ticketsForEvent")
    public String getTicketsForEvent(@RequestParam String event,
                                     @RequestParam String auditorium,
                                     @RequestParam("eventTime") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") LocalDateTime dateTime,
                                     Model model){
        model.addAttribute("tickets", service.getTicketsForEvent(event, auditorium, dateTime));
        return "tickets";
    }
}
