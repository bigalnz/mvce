
@Component
@Controller
@SessionAttributes(value = {"episode", "user"})
public class planController {

    private UserService userService;
    private PlanRepository planRepository;
    private UserRepository userRepository;

    @Autowired
    EpisodeService episodeService;

    @Autowired
    public void setPlanRepository(PlanRepository planRepository) {this.planRepository = planRepository; }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        return user;
    }

    @GetMapping("/newplan")
    public String Episode(Model model, Episode episode, HttpSession session) {
        List<User> users = userService.findAll();
        Map<String,List<UserOrganisationPair>> mapUsersOrgs = userService.getOrganisationLinkedHashMap();
        Plan plan = episode.getPlan();
        model.addAttribute("organisations", mapUsersOrgs);
        model.addAttribute("users", users);
        model.addAttribute("episode", episode);
        model.addAttribute("plan", plan);
        return "newplan";
    }

    @PostMapping("/planSubmit")
    public String submitPlan(@ModelAttribute Plan plan, Episode episode) {
        planRepository.save(plan);
        episodeService.associateEpisodeToPlan(episode.getPlan().getId(), episode);
        episodeService.submitPlan(episode);
        return "redirect:dashboard";
    }
}
