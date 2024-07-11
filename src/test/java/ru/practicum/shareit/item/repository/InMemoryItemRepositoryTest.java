package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.utils.DataUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryItemRepositoryTest {
    InMemoryItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemRepository = new InMemoryItemRepository();
    }

    @Test
    @DisplayName("Test create item functionality")
    public void givenItem_whenCreateItem_thenItemIsCreated() {
        //given
        Item itemToCreate = DataUtils.getItemTestTransient(1);
        User owner = DataUtils.getUserTestPersistence(1);
        itemToCreate.setOwner(owner);
        //when
        Item itemToCreated = itemRepository.create(itemToCreate);
        //then
        assertThat(itemToCreated).isNotNull();
        assertThat(itemToCreated.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test update item functionality")
    public void givenItem_whenUpdateItem_thenItemIsUpdated() {
        //given
        User owner = DataUtils.getUserTestPersistence(1);
        Item itemUpdateName = DataUtils.getItemTestTransient(1);
        itemUpdateName.setOwner(owner);
        String updateName = "updated name";
        itemRepository.create(itemUpdateName);
        //when
        Item itemToUpdate = itemRepository.getById(itemUpdateName.getId()).orElse(null);
        itemToUpdate.setName(updateName);
        Item itemUpdated = itemRepository.update(itemToUpdate);
        //then
        assertThat(itemUpdated).isNotNull();
        assertThat(itemUpdated.getName()).isEqualTo(updateName);
    }

    @Test
    @DisplayName("Test get all of owner item functionality")
    public void givenItems_whenGetAllOfOwner_thenItemsReturned() {
        //given
        User owner1 = DataUtils.getUserTestPersistence(1);
        Item item1 = DataUtils.getItemTestTransient(1);
        Item item2 = DataUtils.getItemTestTransient(2);
        User owner2 = DataUtils.getUserTestPersistence(2);
        Item item3 = DataUtils.getItemTestTransient(3);
        item1.setOwner(owner1);
        item2.setOwner(owner1);
        item3.setOwner(owner2);
        itemRepository.create(item1);
        itemRepository.create(item2);
        itemRepository.create(item3);
        //when
        List<Item> allOfOwner = itemRepository.getAllOfOwner(owner1.getId());
        //then
        assertThat(allOfOwner).isNotEmpty()
                .hasSize(2);
    }

    @Test
    @DisplayName("Test get item by id functionality")
    public void givenItem_whenGetItemById_thenItemIsReturned() {
        //given
        User owner = DataUtils.getUserTestPersistence(1);
        Item item = DataUtils.getItemTestTransient(1);
        item.setOwner(owner);
        itemRepository.create(item);
        //when
        Item itemReturned = itemRepository.getById(item.getId()).orElse(null);
        //then
        assertThat(itemReturned).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Test get item incorrect id functionality")
    public void givenItem_whenGetItemByInvalidId_thenItemIsNotReturned() {
        //given

        //when
        Item item = itemRepository.getById(999L).orElse(null);
        //then
        assertThat(item).isNull();
    }

    @Test
    @DisplayName("Test find item by name or description functionality")
    public void givenItem_whenFindItemByNameByDescription_thenItemIsReturned() {
        //given
        User owner = DataUtils.getUserTestPersistence(1);
        Item item1 = DataUtils.getItemTestTransient(1);
        Item item2 = DataUtils.getItemTestTransient(2);
        Item item3 = DataUtils.getItemTestTransient(3);
        item1.setOwner(owner);
        item2.setOwner(owner);
        item3.setOwner(owner);
        itemRepository.create(item1);
        itemRepository.create(item2);
        itemRepository.create(item3);
        //when
        List<Item> itemsFound = itemRepository.findByNameByDescription("test1");
        //then
        assertThat(itemsFound).isNotEmpty()
                .hasSize(1);
    }
}