package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("AS")
public class AppointmentService implements IService<Appointment> {

    @Autowired
    private IRepository<Appointment> AR;

    @Override
    public void create(Appointment obj) {

    }

    @Override
    public void edit(Appointment obj, String option) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Appointment> fetch(String option) {
        return null;
    }

    @Override
    public Appointment findById(int id) {
        return null;
    }
}