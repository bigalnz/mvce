@Entity
@SessionAttributes(value = {"episode", "user"})
public class Task {

    public Task() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date completeDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date closedDate;
    private String taskText;
    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<TaskNotes> notes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    private User supervisor;
    @ManyToOne
    private User assignee;
    
	// Getters & Setters
}
