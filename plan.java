
@Entity
public class Plan {

    public Plan() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User planLead;
    private String notes;
    @CreationTimestamp
    private Date planCreationDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER) //
    private List<Task> tasks;

    @OneToMany(mappedBy = "plan")
    private List<Episode> episode;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AgencyComment> agencyComments;

    // Getters Setters
