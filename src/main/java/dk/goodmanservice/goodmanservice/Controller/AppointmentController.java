package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Lavet af Markus
 */

@Controller
public class AppointmentController {

    @Autowired
    private IService<Appointment> AS;

    @Autowired
    private IService<User> US;

    @GetMapping("/dashboard/appointments")
    public String appointments(Model model, RedirectAttributes redirect, HttpSession session) {
        try {
            if((int) session.getAttribute("level") > 70) {
                model.addAttribute("appointments", AS.fetch("all"));
            } else {
                model.addAttribute("appointments", AS.fetch(session.getAttribute("id").toString()));
            }
            model.addAttribute("users", US.fetch("all"));
            model.addAttribute("edit", false);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/kalender";
    }

    @GetMapping("/dashboard/appointments/edit/{id}")
    public String appointmentById(@PathVariable("id") int id, Model model, RedirectAttributes redirect, HttpSession session) {
        try {
            model.addAttribute("appointment", AS.findById(id));
            if((int) session.getAttribute("level") > 70) {
                model.addAttribute("appointments", AS.fetch("all"));
            } else {
                model.addAttribute("appointments", AS.fetch(session.getAttribute("id").toString()));
            }
            model.addAttribute("users", US.fetch("all"));
            model.addAttribute("edit", true);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/kalender";
    }

    @PostMapping("/dashboard/appointment/edit")
    public String appointmentEdit(@ModelAttribute Appointment obj, RedirectAttributes redirect) {
        try {
            String msg = AS.edit(obj);
            redirect.addFlashAttribute("msg", msg);

            if(!msg.equals("REDIGERET")) {
                return "redirect:/dashboard/appointments/edit/" + obj.getId();
            }
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/appointments";
    }

    @PostMapping("/dashboard/appointment/delete/{id}")
    public String deleteAppointment(@PathVariable("id") int id, RedirectAttributes redirect) {
        try {
            redirect.addFlashAttribute("msg", AS.delete(id));
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/appointments";
    }

    @PostMapping("/dashboard/appointment/create")
    public String createAppointment(@ModelAttribute Appointment obj, RedirectAttributes redirect) {
        try {
            redirect.addFlashAttribute("msg", AS.create(obj));
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/appointments";
    }
}