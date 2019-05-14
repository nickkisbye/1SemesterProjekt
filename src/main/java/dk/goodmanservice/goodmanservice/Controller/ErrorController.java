package dk.goodmanservice.goodmanservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, @ModelAttribute("errorCode") String errorCode, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "ErrorHandling/error404";
            }
        }
        else if(!errorCode.equals("0")) {
            model.addAttribute("errorCode", errorCode);
        }
        return "ErrorHandling/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
