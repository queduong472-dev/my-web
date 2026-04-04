package com.example.my_web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    // SỬA TẠI ĐÂY NÈ MÁ: Để trang chủ hiện được ảnh Vision Board
    @GetMapping("/")
    public String indexPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("visions", visionItemRepository.findAll());
        return "index";
    }

    // --- QUẢN LÝ VISION BOARD ---
    @GetMapping("/vision")
    public String visionPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("visions", visionItemRepository.findAll());
        return "vision";
    }

    @PostMapping("/add-vision")
    public String addVision(@RequestParam String imageUrl) {
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            visionItemRepository.save(new VisionItem(null, imageUrl));
        }
        return "redirect:/vision";
    }

    @PostMapping("/delete-vision")
    public String deleteVision(@RequestParam Long id) {
        visionItemRepository.deleteById(id);
        return "redirect:/vision";
    }

    // --- QUẢN LÝ GALLERY ---
    @GetMapping("/gallery")
    public String galleryPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("artworks", artworkRepository.findAll());
        return "gallery";
    }

    @PostMapping("/add-art")
    public String addArt(@RequestParam String title, @RequestParam String imageUrl) {
        if (title != null && !title.trim().isEmpty() && imageUrl != null) {
            artworkRepository.save(new Artwork(title, imageUrl));
        }
        return "redirect:/gallery";
    }

    @PostMapping("/delete-art")
    public String deleteArt(@RequestParam Long id) {
        artworkRepository.deleteById(id);
        return "redirect:/gallery";
    }

    // --- QUẢN LÝ DIARY ---
    @GetMapping("/diary")
    public String diaryPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("diaryList", diaryRepository.findAll());
        return "diary";
    }

    @PostMapping("/add-diary")
    public String addDiary(@RequestParam String content) {
        if (content != null && !content.trim().isEmpty()) {
            diaryRepository.save(new Diary(content));
        }
        return "redirect:/diary";
    }

    @PostMapping("/update-diary")
    public String updateDiary(@RequestParam Long id, @RequestParam String content) {
        Diary existingDiary = diaryRepository.findById(id).orElse(null);
        if (existingDiary != null && content != null && !content.trim().isEmpty()) {
            existingDiary.setContent(content);
            diaryRepository.save(existingDiary);
        }
        return "redirect:/diary";
    }

    @PostMapping("/delete-diary")
    public String deleteDiary(@RequestParam Long id) {
        diaryRepository.deleteById(id);
        return "redirect:/diary";
    }

    // --- QUẢN LÝ ROUTINE ---
    @GetMapping("/routine")
    public String routinePage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("routineList", routineRepository.findAll());
        return "routine";
    }

    @PostMapping("/add-routine")
    public String addRoutine(@RequestParam String content, @RequestParam String session) {
        routineRepository.save(new Routine(content, session));
        return "redirect:/routine";
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

    // --- QUẢN LÝ DAILY, MUSIC, WRITING ---
    @GetMapping("/daily")
    public String dailyPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("dailyList", dailyList);
        return "daily";
    }

    @GetMapping("/add-daily")
    public String addDaily(@RequestParam String date, @RequestParam String grateful, @RequestParam int rating, @RequestParam String emotion) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("id", System.currentTimeMillis());
        entry.put("date", date);
        entry.put("grateful", grateful);
        entry.put("rating", rating);
        entry.put("emotion", emotion);
        dailyList.add(entry);
        return "redirect:/daily";
    }

    @PostMapping("/delete-daily")
    public String deleteDaily(@RequestParam Long id) {
        dailyList.removeIf(entry -> entry.get("id").equals(id));
        return "redirect:/daily";
    }

    @GetMapping("/music")
    public String musicPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("songsList", songRepository.findAll());
        model.addAttribute("exercisesList", exerciseRepository.findAll());
        return "music";
    }

    @GetMapping("/add-song")
    public String addSong(@RequestParam String title, @RequestParam String spotifyId) {
        songRepository.save(new Song(title, spotifyId));
        return "redirect:/music";
    }

    @PostMapping("/delete-song")
    public String deleteSong(@RequestParam Long id) {
        songRepository.deleteById(id);
        return "redirect:/music";
    }

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

    @PostMapping("/delete-idea")
    public String deleteIdea(@RequestParam Long id) {
        ideaRepository.deleteById(id);
        return "redirect:/writing";
    }

    // --- MỤC ACADEMICS (SCHOOL) ---
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

    @PostMapping("/delete-academic-task")
    public String deleteAcademicTask(@RequestParam Long id) {
        academicTaskRepository.deleteById(id);
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

    // --- MỤC MEDIA LOG (LOGS) ---
    @GetMapping("/medialog")
    public String mediaLogPage(Model model, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) return "redirect:/login";
        model.addAttribute("mediaList", mediaItemRepository.findAll());
        return "medialog";
    }

    @PostMapping("/add-media")
    public String addMedia(@RequestParam String title, @RequestParam String type, @RequestParam String status) {
        mediaItemRepository.save(new MediaItem(null, title, "", status, 5, type));
        return "redirect:/medialog";
    }

    @PostMapping("/delete-media")
    public String deleteMedia(@RequestParam Long id) {
        mediaItemRepository.deleteById(id);
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
            if ("imageUrl".equals(field)) item.setImageUrl(value);
            mediaItemRepository.save(item);
            return "OK";
        }
        return "Error";
    }
}