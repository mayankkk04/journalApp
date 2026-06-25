package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional //Follows ACID properties
//    Look at your saveEntry method:It saves a JournalEntry to MongoDB.It adds that entry to the User object.It updates the User document in MongoDB.
    public void saveEntry(JournalEntry journalEntry, String username){
        try{
            User user = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved); //as it is a list
            userService.saveUser(user);
        }catch(Exception e){
            throw new RuntimeException("An error occured: ",e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        try{
            journalEntryRepository.save(journalEntry);
        }catch(Exception e){
            log.error("Exception "+e);
        }

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        boolean removed =false;
        try {
            User user = userService.findByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));//Delete from user
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id); //delete from JournalEntries
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An Error occured.",e);
        }
        return removed;
    }

}
