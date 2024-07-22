package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validationMarker.Marker;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;
    private static final String USER_ID = "X-Sharer-User-Id";

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public ItemDto create(@RequestBody @Valid ItemDto itemDto, @RequestHeader(USER_ID) long userId) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{id}")
    @Validated(Marker.OnUpdate.class)
    public ItemDto update(@PathVariable Long id, @RequestBody @Valid ItemDto itemDto,
                          @RequestHeader(USER_ID) long userId) {
        itemDto.setId(id);
        return itemService.update(itemDto, userId);
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @GetMapping
    public List<ItemDto> getAllOfOwner(@RequestHeader(USER_ID) long userId) {
        return itemService.getAllOfOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> findByNameByDescription(@RequestParam String text) {
        return itemService.findByNameByDescription(text);
    }
}
