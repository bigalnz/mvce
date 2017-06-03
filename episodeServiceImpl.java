
@Service
@Component
public class EpisodeServiceImpl implements EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final PersonService personService;
    private final AddressService addressService;
    private final PlanRepository planRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public EpisodeServiceImpl(EpisodeRepository episodeRepository, PersonService personService, AddressService addressService, PlanRepository planRepository, TaskRepository taskRepository) {
        this.episodeRepository = episodeRepository;
        this.personService = personService;
        this.addressService = addressService;
        this.planRepository = planRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Episode saveEpisode(Episode episode) {
        // Assert.notNull(episode, "The episode argument can not be null");

        // CHECK FOR EXISTING PEOPLE ON NID
        List mergedPeople = personService.mergeDetachedWithAttached( episode.getPersons() );
        episode.setPersons( mergedPeople );
        // Episode savedEpisode = episodeRepository.save(episode);
        return episodeRepository.save(episode);
    }

    @Override
    @Transactional
    public Episode submitPlan(Episode episode) {
        System.out.println("here");
        // SAVE PLAN & TASKS
        List<Episode> episodeList = new ArrayList<>();
        episodeList.add(episode);
        episode.getPlan().setEpisode(episodeList);

        // FOR EACH TASK THAT DOESNT HAVE PLAN_ID - ADD IT
        for (Task tasks : episode.getPlan().getTasks()) {
            if (tasks.getPlan() == null) {
                tasks.setPlan(episode.getPlan());
            }
        }

        planRepository.save(episode.getPlan());
        taskRepository.save(episode.getPlan().getTasks());

        // SETUP PLAN TO KNOW WHAT EPISODES ITS RELATED TO (A COLLETION)
        // episode.getPlan().getEpisode();

        // A TEST TO NAVIGATE FROM BOTTOM UP
        List<Task> tempTask = episode.getPlan().getTasks();
        Task task = tempTask.get(2);
        Plan testPlan = task.getPlan();
        List<Episode> testEpisode = testPlan.getEpisode();

        // SAVE EPISODE
        return episodeRepository.save(episode);
    }

    @Override
    @Transactional
    public Episode associateEpisodeToPlan(Long planId, Episode newEpisode) {
        final Plan plan = planRepository.findOne( planId );
        plan.getEpisode().add( newEpisode);
        newEpisode.setPlan( plan );
        return episodeRepository.save( newEpisode );
    }




}
