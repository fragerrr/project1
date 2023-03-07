package kz.springcourse.demo.service;

import kz.springcourse.demo.model.Person;
import kz.springcourse.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> getUsers(){
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person getUser(Integer id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addUser(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void updateUser(Person person, Integer id){
        Person personToBeUpdated = personRepository.findById(id).orElse(null);

        if(personToBeUpdated != null){
            personToBeUpdated.setAge(person.getAge());
            personToBeUpdated.setName(person.getName());
        }
    }

    @Transactional
    public void deleteUser(Integer id){
        personRepository.delete(personRepository.findById(id).orElse(null));
    }

}
