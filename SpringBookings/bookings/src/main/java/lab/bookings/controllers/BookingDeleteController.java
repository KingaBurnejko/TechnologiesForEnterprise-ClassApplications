package lab.bookings.controllers;

import lab.bookings.repositories.BookingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingDeleteController {
    private final BookingRepository bookingRepository;

    public BookingDeleteController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @PostMapping("/bookings/delete")
    public String deleteBooking(@RequestParam Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/bookings";
    }
}
