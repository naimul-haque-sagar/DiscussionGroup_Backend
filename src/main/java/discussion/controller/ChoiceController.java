package discussion.controller;

import discussion.dto.ChoiceDto;
import discussion.service.ChoiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/choice")
@AllArgsConstructor
public class ChoiceController {
    private final ChoiceService choiceService;

    @PostMapping
    public ResponseEntity choice(@RequestBody ChoiceDto choiceDto){
        choiceService.choice(choiceDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
