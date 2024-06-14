package com.qkunkii.qkn.controllers;

import com.qkunkii.qkn.models.Post;
import com.qkunkii.qkn.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    public PostRepository postRepository;
    public static String UploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";

    @GetMapping("/posts")
    public String postsMain(Model model) {
        model.addAttribute("title", "Blog");
        model.addAttribute("text", "Posts");
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }
    @GetMapping ("/post/{id}")
    public String OnePost (@PathVariable(value = "id") Long id, Model model){
        if(!postRepository.existsById(id)) {
            return "redirect:/posts";
        }
        model.addAttribute ( "title",  "Редагування");
        model.addAttribute( "h1",  "Редагування статті");
        Optional<Post> postt = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        postt.ifPresent(res::add);
        model.addAttribute( "posts", res);
        return "post";
    }
    @GetMapping("/post/{id}/delete")
    public String DeletePost (@PathVariable(value = "id") Long id, Model model){
        if(!postRepository.existsById(id)) {
            return "posts";
        }
        model.addAttribute ( "title",  "Deleting");
        model.addAttribute( "h1",  "Deleting");
        postRepository.deleteById(id);
        return "redirect:/posts";
    }
    @GetMapping("/posts/create")
    public String CreatePost(Model model) {
        model.addAttribute("title", "Add post");
        model.addAttribute("text", "Add post");
        return "addpost";
    }
    @PostMapping("/posts/create")
    public String AddPostToDb(@RequestParam String name, @RequestParam String short_desc, @RequestParam String full_desc, @RequestParam int downloads, @RequestParam int wishlisted, @RequestParam ("image") MultipartFile file) throws IOException {
        StringBuilder filename = new StringBuilder();
        Path filenameandpath = (Paths.get(UploadDir, file.getOriginalFilename()));
        filename.append(file.getOriginalFilename());
        Files.write(filenameandpath, file.getBytes());
        String image = filename.toString();
        Post post = new Post(name, short_desc, full_desc, image, wishlisted, downloads);
        postRepository.save(post);
        return "redirect:/posts";
    }
    @GetMapping ("/post/{id}/edit")
    public String PostEdit (@PathVariable(value = "id") Long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/posts";
        }
        model.addAttribute ( "title",  "Редагування");
        model.addAttribute( "h1",  "Редагування статті");
        Optional<Post> postt = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        postt.ifPresent(res::add);
        model.addAttribute( "posts", res);
        return "postedit";
    }
    @PostMapping ("/post/{id}/edited")
    public String PostEditConf (@RequestParam String name, @RequestParam String short_desc, @RequestParam String full_desc, @RequestParam int downloads, @RequestParam int wishlisted, @RequestParam ("image") MultipartFile file, @PathVariable(value = "id") Long id, @RequestParam String oldphoto) throws IOException {
        String image = "";
        File oldp = new File(UploadDir + oldphoto);
        if(Objects.equals(file.getOriginalFilename(), "") ) {
            //file is not selected = noimage
            if (Objects.equals(oldphoto, "noimage.jpg") || Objects.equals(oldphoto, "errordel.jpg") || Objects.equals(oldphoto, "")) {
                image = "noimage.jpg";
                Post post = new Post(id, name, short_desc, full_desc, image, wishlisted, downloads);
                postRepository.deleteById(id);
                postRepository.save(post);
                return "redirect:/posts";
            }
            else {
                try {
                    oldp.delete();
                }
                catch (Exception e) {}
                Path filenameandpath;
                if(!oldp.exists()) {
                    image = "noimage.jpg";
                    filenameandpath = (Paths.get(UploadDir, image));
                    Post post = new Post(id, name, short_desc, full_desc, image, wishlisted, downloads);
                    postRepository.deleteById(id);
                    postRepository.save(post);
                    return "redirect:/posts";
                }
                else if(oldp.exists()){
                    image = "errordel.jpg";
                    filenameandpath = (Paths.get(UploadDir, image));
                }
                else{
                    image = "noimage.jpg";
                    filenameandpath = (Paths.get(UploadDir, image));
                }
                image = "noimage.jpg";
                filenameandpath = (Paths.get(UploadDir, image));
                Post post = new Post(id, name, short_desc, full_desc, image, wishlisted, downloads);
                postRepository.deleteById(id);
                postRepository.save(post);
                return "redirect:/posts";
            }
        }
        else {
            image = file.getOriginalFilename();
            Path filenameandpath = (Paths.get(UploadDir, image));
            if (!Objects.equals(oldphoto, "noimage.jpg") || !Objects.equals(oldphoto, "errordel.jpg") || !Objects.equals(oldphoto, "")) {
                image = file.getOriginalFilename();
                filenameandpath = (Paths.get(UploadDir, image));
                Files.write(filenameandpath, file.getBytes());
                oldp.delete();
                Post post = new Post(id, name, short_desc, full_desc, image, wishlisted, downloads);
                postRepository.deleteById(id);
                postRepository.save(post);
                return "redirect:/posts";
            }
            else {
                oldp.delete();
                if(!oldp.exists()) {
                    image = file.getOriginalFilename();
                    filenameandpath = (Paths.get(UploadDir, image));
                    Files.write(filenameandpath, file.getBytes());
                }
                else if(oldp.exists()){
                    image = "errordel.jpg";
                    filenameandpath = (Paths.get(UploadDir, image));
                }
                else{
                    image = "noimage.jpg";
                    filenameandpath = (Paths.get(UploadDir, image));
                }
            }
        }
        Post post = new Post(id, name, short_desc, full_desc, image, wishlisted, downloads);
        postRepository.deleteById(id);
        postRepository.save(post);
        return "redirect:/posts";
    }
}