package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import dk.goodmanservice.goodmanservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@Component("US")
public class UserService implements IService<User> {

    @Autowired
    private IRepository<User> UR;

    @Override
    public void create(User obj) {

    }

    @Override
    public void edit(User obj, String option) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<User> fetch(String option) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }
}