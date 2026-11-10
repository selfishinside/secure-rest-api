package ru.itmo.securerestapi.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import ru.itmo.securerestapi.model.SecureData;
import ru.itmo.securerestapi.repository.DataRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {

    private final DataRepository repository;

    public DataController(DataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/data")
    public List<Map<String, Object>> getData() {
        return repository.findAll()
                .stream()
                .map(item -> {
                    Map<String, Object> result = new LinkedHashMap<>();

                    result.put("id", item.getId());
                    result.put(
                            "title",
                            HtmlUtils.htmlEscape(item.getTitle())
                    );
                    result.put(
                            "content",
                            HtmlUtils.htmlEscape(item.getContent())
                    );
                    result.put(
                            "createdBy",
                            HtmlUtils.htmlEscape(item.getCreatedBy())
                    );

                    return result;
                })
                .toList();
    }

    @PostMapping("/data")
    public ResponseEntity<Map<String, Object>> createData(
            @Valid @RequestBody CreateDataRequest request,
            Authentication authentication
    ) {
        SecureData data = new SecureData(
                request.title(),
                request.content(),
                authentication.getName()
        );

        SecureData saved = repository.save(data);

        return ResponseEntity.ok(Map.of(
                "message", "Данные сохранены",
                "id", saved.getId()
        ));
    }
}