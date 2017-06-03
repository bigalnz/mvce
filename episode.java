
@Entity
public class Episode {

    public Episode() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_date")
    @DateTimeFormat (pattern="dd/MM/yyyy HH:mm")
    private Date start_date;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "episode_person", joinColumns = @JoinColumn(name = "episode_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"))
    private List<Person> persons;

    @OneToOne(cascade=CascadeType.ALL)
    private Address address;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Stat> stats;

    @ManyToOne(cascade = CascadeType.ALL)
    private Plan plan;

	// Getters & Setters
	
}
