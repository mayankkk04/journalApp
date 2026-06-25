package net.engineeringdigest.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true) //username must be unique
    @NonNull //cannot be null
    private String username;
    @NonNull
    private String password;

    @DBRef //creating refrences of Journal entries in user
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles; //assigning user roles like admin etc
}
