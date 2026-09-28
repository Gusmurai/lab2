package ru.kafpin.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kafpin.lab2.entity.Person;
import ru.kafpin.lab2.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    // отобразить все
    @GetMapping
    public String mainPage(Model model) {
        List<Person> allPersons = personRepository.findAll();
        model.addAttribute("persons", allPersons);
        return "main";
    }

    // один человек по id
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty()) {
            return "redirect:/persons";
        }
        model.addAttribute("selectedPerson", optionalPerson.get());
        return "details";
    }

    // добавления нового человека
    @GetMapping("/add")
    public String addPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "edit_person";
    }

    // редактирование существующего человека
    @GetMapping("/update/{id}")
    public String editPersonForm(@PathVariable("id") Long id, Model model) {
        // проверка существования
        if (!personRepository.existsById(id)) {
            return "redirect:/persons";
        }
        Optional<Person> optionalPerson = personRepository.findById(id);
        model.addAttribute("person", optionalPerson.get());
        return "edit_person";
    }

    // сохранение (создание или обновление)
    @PostMapping("/save")
    public String savePerson(@ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/persons";
    }

    // удаление по id
    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        // Проверка существования перед удалением
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }
        return "redirect:/persons";
    }
}