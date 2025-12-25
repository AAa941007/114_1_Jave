import java.io.Serializable;

/**
 * Person - The highest-level abstract class.
 * Defines the core attributes for any individual in the system.
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected String phone;
    protected String email;

    public Person(String id, String name, String phone, String email) {
        // Validation check as per specification [cite: 573]
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("id cannot be null or empty");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("name cannot be null or empty");
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    // Abstract method to be implemented by all subclasses [cite: 573]
    public abstract void displayInfo();

    @Override
    public String toString() {
        return String.format("%s (ID: %s)", name, id);
    }
}