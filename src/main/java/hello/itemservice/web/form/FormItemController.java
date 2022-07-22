package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryType;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        log.info("item={}", item);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

    @ModelAttribute("regionMap")
    public Map<String, String> regionMap() {
        Map<String, String> regionMap = new LinkedHashMap<>();
        regionMap.put("SEOUL", "서울");
        regionMap.put("BUSAN", "부산");
        regionMap.put("JEJU", "제주");
        return regionMap;
    }

    @ModelAttribute("itemTypeArray")
    public ItemType[] itemTypeArray() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryTypeList")
    public List<DeliveryType> deliveryTypeList() {
        List<DeliveryType> deliveryTypeList = new ArrayList<>();
        deliveryTypeList.add(new DeliveryType("FAST", "빠른 배송"));
        deliveryTypeList.add(new DeliveryType("NORMAL", "보통 배송"));
        deliveryTypeList.add(new DeliveryType("SLOW", "느린 배송"));
        return deliveryTypeList;
    }

}

