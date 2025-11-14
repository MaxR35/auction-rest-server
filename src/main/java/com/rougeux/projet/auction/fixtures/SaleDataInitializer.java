package com.rougeux.projet.auction.fixtures;

import com.rougeux.projet.auction.bo.Item;
import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IItemDao;
import com.rougeux.projet.auction.dal.ISaleDao;
import com.rougeux.projet.auction.dal.IUserDao;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Order(4)
@Component
public class SaleDataInitializer implements CommandLineRunner {

    private final Faker faker = new Faker();

    // Non-sensitive use of Random (data seeding only)
    private final Random random = new Random();
    private final IItemDao itemdao;
    private final IUserDao userdao;
    private final ISaleDao saledao;


    public SaleDataInitializer(IItemDao itemdao, IUserDao userdao, ISaleDao saledao) {
        this.itemdao = itemdao;
        this.userdao = userdao;
        this.saledao = saledao;
    }

    @Override
    public void run(String... args) throws Exception {
        if(saledao.checkData() > 0) {
            return;
        }
        List<Item> items = itemdao.findAll();
        List<User> users = userdao.findAll();

        items.forEach(item -> {
            Sale sale = new Sale();
            String slug = item.getName()
                    .toLowerCase()
                    .replaceAll("[^a-z0-9]+", "-")
                    .replaceAll("^-|-$", "")
                    + "-" + UUID.randomUUID().toString().substring(0, 8);

            sale.setId(null);
            sale.setSlug(slug);
            sale.setCreateAt(LocalDateTime.now().minusDays(random.nextInt(18)));
            sale.setStartAt(LocalDateTime.now().minusDays(random.nextInt(7)));
            sale.setEndAt(LocalDateTime.now().plusDays(random.nextInt(30)));
            sale.setStartingPrice(faker.number().numberBetween(20, 100));
            sale.setLikes(random.nextInt(384));

            sale.setItem(item);
            sale.setSeller(users.get(random.nextInt(users.size())));

            saledao.save(sale);
        });
    }
}
