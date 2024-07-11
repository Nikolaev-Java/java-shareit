package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
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

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@PathVariable Long id, @RequestBody ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.update(id, itemDto, userId);
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @GetMapping
    public List<ItemDto> getAllOfOwner(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllOfOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> findByNameByDescription(@RequestParam String text) {
        return itemService.findByNameByDescription(text);
    }
}
