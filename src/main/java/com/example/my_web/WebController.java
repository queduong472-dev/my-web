package com.example.my_web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.text.SimpleDateFormat;

@Controller
public class WebController {

    @Autowired private IdeaRepository ideaRepository;
    @Autowired private SongRepository songRepository;
    @Autowired private ExerciseRepository exerciseRepository;
    @Autowired private RoutineRepository routineRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private ArtworkRepository artworkRepository;
    @Autowired private AcademicTaskRepository academicTaskRepository;
    @Autowired private MediaItemRepository mediaItemRepository;
    @Autowired private VisionItemRepository visionItemRepository;

    private List<Map<String, Object>> dailyList = new ArrayList<>();

    // --- ĐĂNG NHẬP & BẢO VỆ ---
    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String password, HttpSession session, Model model) {
        if ("123456".equals(password)) {
            session.setAttribute("isLoggedIn", true);
            return "redirect:/";
        }
        model.addAttribute("error", "Sai mật mã rồi Minh ảnh ơi! 🙀");
        return "login";
    }

    @GetMapping("/")
    public String indexPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("visions", visionItemRepository.findAll());
        return "index";
    }

    // --- 1. QUẢN LÝ ROUTINE ---
    @GetMapping("/routine")
    public String routinePage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("routineList", routineRepository.findAll());
        return "routine";
    }

    @PostMapping("/add-routine")
    public String addRoutine(@RequestParam String content, @RequestParam String session) {
        if (content != null && !content.trim().isEmpty()) {
            Routine newRt = new Routine();
            newRt.setContent(content);
            newRt.setRoutineSession(session); 
            newRt.setCompleted(false);
            routineRepository.save(newRt);
        }
        return "redirect:/routine";
    }

    @PostMapping("/update-routine-status")
    @ResponseBody
    public String updateRoutineStatus(@RequestParam Long id, 
                                    @RequestParam(required = false) Boolean completed, 
                                    @RequestParam(required = false) String content) {
        Routine rt = routineRepository.findById(id).orElse(null);
        if (rt != null) {
            if (completed != null) rt.setCompleted(completed);
            if (content != null) rt.setContent(content);
            routineRepository.save(rt);
            return "OK";
        }
        return "Error";
    }

    @GetMapping("/toggle-routine")
    @ResponseBody
    public String toggleRoutine(@RequestParam Long id, @RequestParam boolean completed) {
        Routine rt = routineRepository.findById(id).orElse(null);
        if (rt != null) {
            rt.setCompleted(completed);
            routineRepository.save(rt);
            return "OK";
        }
        return "Error";
    }

    @PostMapping("/delete-routine")
    public String deleteRoutine(@RequestParam Long id) {
        routineRepository.deleteById(id);
        return "redirect:/routine";
    }

    // --- 2. QUẢN LÝ NHẬT KÝ LINH TINH (DIARY) ---
    @GetMapping("/diary")
    public String diaryPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("diaryList", diaryRepository.findAll());
        return "diary";
    }

    @PostMapping("/add-diary")
    public String addDiary(@RequestParam String content) {
        if (content != null && !content.trim().isEmpty()) {
            Diary diary = new Diary(content);
            diary.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            diaryRepository.save(diary);
        }
        return "redirect:/diary";
    }

    @PostMapping("/update-diary")
    @ResponseBody 
    public String updateDiary(@RequestParam Long id, @RequestParam String content) {
        Diary diary = diaryRepository.findById(id).orElse(null);
        if (diary != null) {
            diary.setContent(content);
            diaryRepository.save(diary);
            return "OK";
        }
        return "Error";
    }

    @PostMapping("/delete-diary")
    public String deleteDiary(@RequestParam Long id) {
        diaryRepository.deleteById(id);
        return "redirect:/diary";
    }

    // --- 3. QUẢN LÝ WRITING (BRAIN DUMP) ---
    @GetMapping("/writing")
    public String writingPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("ideasList", ideaRepository.findAll());
        return "writing";
    }

    @PostMapping("/add-idea")
    public String addIdea(@RequestParam String content) {
        if (content != null && !content.trim().isEmpty()) ideaRepository.save(new Idea(content));
        return "redirect:/writing";
    }

    @PostMapping("/update-idea")
    public String updateIdea(@RequestParam Long id, @RequestParam String content) {
        Idea idea = ideaRepository.findById(id).orElse(null);
        if (idea != null && content != null && !content.trim().isEmpty()) {
            idea.setContent(content);
            ideaRepository.save(idea);
        }
        return "redirect:/writing";
    }

    @PostMapping("/delete-idea")
    public String deleteIdea(@RequestParam Long id) {
        ideaRepository.deleteById(id);
        return "redirect:/writing";
    }

    // --- 4. QUẢN LÝ ACADEMICS (KANBAN) ---
    @GetMapping("/academics")
    public String academicsPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("todoTasks", academicTaskRepository.findByStatus("TODO"));
        model.addAttribute("doingTasks", academicTaskRepository.findByStatus("DOING"));
        model.addAttribute("doneTasks", academicTaskRepository.findByStatus("DONE"));
        return "academics";
    }

    @PostMapping("/add-academic-task")
    public String addAcademicTask(@RequestParam String content, @RequestParam String status) {
        if (content != null && !content.trim().isEmpty()) {
            academicTaskRepository.save(new AcademicTask(null, content, status));
        }
        return "redirect:/academics";
    }

    @PostMapping("/update-academic-task")
    @ResponseBody
    public String updateAcademicTask(@RequestParam Long id, @RequestParam String content) {
        AcademicTask task = academicTaskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setContent(content);
            academicTaskRepository.save(task);
            return "OK";
        }
        return "Error";
    }

    @PostMapping("/delete-academic-task")
    public String deleteAcademicTask(@RequestParam Long id) {
        academicTaskRepository.deleteById(id);
        return "redirect:/academics";
    }

    // --- 5. QUẢN LÝ MEDIA LOG (FIX LỖI MẤT ẢNH) ---
    @GetMapping("/medialog")
    public String mediaLogPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("mediaList", mediaItemRepository.findAll());
        return "medialog";
    }

    @PostMapping("/add-media")
    public String addMedia(@RequestParam String title, @RequestParam String type, @RequestParam String status) {
        // Khởi tạo ảnh mặc định để không bị trắng card
        mediaItemRepository.save(new MediaItem(null, title, "", status, 5, type));
        return "redirect:/medialog";
    }

    @PostMapping("/update-media")
    @ResponseBody
    public String updateMedia(@RequestParam Long id, @RequestParam String field, @RequestParam String value) {
        MediaItem item = mediaItemRepository.findById(id).orElse(null);
        if (item != null) {
            if ("title".equals(field)) item.setTitle(value);
            if ("status".equals(field)) item.setStatus(value);
            if ("type".equals(field)) item.setType(value);
            if ("rating".equals(field)) item.setRating(Integer.parseInt(value));
            // Dòng này để lưu link ảnh vào ổ D đây má ơi!
            if ("imageUrl".equals(field)) item.setImageUrl(value); 
            
            mediaItemRepository.save(item);
            return "OK";
        }
        return "Error";
    }

    @PostMapping("/delete-media")
    public String deleteMedia(@RequestParam Long id) {
        mediaItemRepository.deleteById(id);
        return "redirect:/medialog";
    }

    // --- CÁC MỤC KHÁC (VISION, GALLERY, MUSIC) ---
    @GetMapping("/vision")
    public String visionPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("visions", visionItemRepository.findAll());
        return "vision";
    }

    @PostMapping("/add-vision")
    public String addVision(@RequestParam String imageUrl) {
        if (imageUrl != null && !imageUrl.trim().isEmpty()) visionItemRepository.save(new VisionItem(null, imageUrl));
        return "redirect:/vision";
    }

    @PostMapping("/delete-vision")
    public String deleteVision(@RequestParam Long id) {
        visionItemRepository.deleteById(id);
        return "redirect:/vision";
    }

    @GetMapping("/gallery")
    public String galleryPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("artworks", artworkRepository.findAll());
        return "gallery";
    }

    @PostMapping("/add-art")
    public String addArt(@RequestParam String title, @RequestParam String imageUrl) {
        if (title != null && !title.trim().isEmpty()) artworkRepository.save(new Artwork(title, imageUrl));
        return "redirect:/gallery";
    }

    @PostMapping("/delete-art")
    public String deleteArt(@RequestParam Long id) {
        artworkRepository.deleteById(id);
        return "redirect:/gallery";
    }

    @GetMapping("/music")
    public String musicPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("songsList", songRepository.findAll());
        model.addAttribute("exercisesList", exerciseRepository.findAll());
        return "music";
    }

    @PostMapping("/add-song")
    public String addSong(@RequestParam String title, @RequestParam String spotifyId) {
        if (title != null && !title.trim().isEmpty()) songRepository.save(new Song(title, spotifyId));
        return "redirect:/music";
    }

    @PostMapping("/delete-song")
    public String deleteSong(@RequestParam Long id) {
        songRepository.deleteById(id);
        return "redirect:/music";
    }

    @PostMapping("/add-exercise")
    public String addExercise(@RequestParam String content) {
        if (content != null && !content.trim().isEmpty()) exerciseRepository.save(new Exercise(content));
        return "redirect:/music";
    }

    @PostMapping("/delete-exercise")
    public String deleteExercise(@RequestParam Long id) {
        exerciseRepository.deleteById(id);
        return "redirect:/music";
    }

    @PostMapping("/toggle-exercise")
    public String toggleExercise(@RequestParam Long id) {
        Exercise ex = exerciseRepository.findById(id).orElse(null);
        if (ex != null) {
            ex.setCompleted(!ex.isCompleted());
            exerciseRepository.save(ex);
        }
        return "redirect:/music";
    }
}