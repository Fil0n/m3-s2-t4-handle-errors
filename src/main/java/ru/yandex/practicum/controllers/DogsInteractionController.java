package ru.yandex.practicum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exeptions.HappinessOverflowException;
import ru.yandex.practicum.exeptions.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/dogs")
public class DogsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        if(happiness > 10) {
            throw new HappinessOverflowException(happiness);
        }
        happiness += 2;
        return Map.of("talk", "Гав!");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count имеет отрицательное значение.");
        }

        if(happiness > 10) {
            throw new HappinessOverflowException(happiness);
        }

        happiness += count;
        return Map.of("action", "Вильнул хвостом. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @ExceptionHandler
    public Map<String, String> handle(final IncorrectCountException e) {
        return Map.of("error", "Ошибка с параметром count.",
                "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse RuntimeException(final RuntimeException e) {
        return new ErrorResponse("Ошибка с параметром count.", e.getMessage());
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHappinessOverflow(final HappinessOverflowException e) {
        return new ErrorResponse(
                "Слишком большое значение [happiness]",
                "Осторожно, вы так избалуете пёсика! Уровень happinness: " + e.getHappinessLevel().toString());
    }
}
