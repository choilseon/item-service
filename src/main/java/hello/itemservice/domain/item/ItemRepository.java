package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // 실제 사용 시 멀티 스레드 환경 고려해서 ConcurrentHashMap 사용해야함
    private static long sequence = 0L; // 멀티 스레드 환경 고려해서 AtomicLong 등 사용해야함(동시 접근 시 값이 꼬일 수 있음)

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values()); // 리턴된 리스트에 변화가 있어도 store에 영향 미치지 않도록 감싸서 리턴
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
