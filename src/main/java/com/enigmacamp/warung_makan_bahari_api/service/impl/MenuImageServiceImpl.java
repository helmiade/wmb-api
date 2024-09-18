package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.MenuImage;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuImageRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Repository
@RequiredArgsConstructor
public class MenuImageServiceImpl implements MenuImageService {
    private final MenuImageRepository menuImageRepository;
    private final Path directoryPath= Paths.get("/home/enigma/Documents/Enigmacamp/asset/images");


    @Override
    public MenuImage createFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"file is empty");
        try {
            //logic utk menyimpan file di directory
            Files.createDirectories(directoryPath);
            //penamaan file
            //%d dipakai utk mengambil nilai desimal dri currenttime, %s dipakai agar jadi string
            String fileName = String.format("%d %s", System.currentTimeMillis(), multipartFile.getOriginalFilename());
            //lokasi file beserta path directory
            Path filePath=directoryPath.resolve(fileName);
            //penyimpanan
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            //logic utk menyimpan di database
            MenuImage menuImage= MenuImage.builder()
                    .name(fileName)
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .path(filePath.toString())
                    .build();

            return menuImageRepository.saveAndFlush(menuImage);

        }catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());

        }
    }

    @Override
    public Resource findByPath(String path) {
        Path filePath=Paths.get(path);
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @Override
    public void deleteFile(String path) {

    }
}


