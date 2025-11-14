package com.rougeux.projet.auction.fixtures;

import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Order(1)
@Component
public class UserDataInitializer implements CommandLineRunner {

    private final Faker faker = new Faker();

    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder;
    private final IUserDao userDao;
    private final Environment environment;

    public UserDataInitializer(PasswordEncoder passwordEncoder, IUserDao userDao, Environment environment) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> LST_IMG = List.of(
                "pexels-1211979-3444087.jpg",
                "pexels-ahmet-i-hsan-tezcan-2284826-19683225.jpg",
                "pexels-alipazani-2811089.jpg",
                "pexels-amir-selfish-2150257461-33823988.jpg",
                "pexels-andreza-abras-2155351373-33785316.jpg",
                "pexels-andreza-abras-2155351373-33785321.jpg",
                "pexels-andy-lee-1453672476-33852637.jpg",
                "pexels-bertellifotografia-33714958.jpg",
                "pexels-caner-kokcu-636242728-19420351.jpg",
                "pexels-cottonbro-5082976.jpg",
                "pexels-cottonbro-7760234.jpg",
                "pexels-creationhill-1681010.jpg",
                "pexels-croft-alexander-747385-1624229.jpg",
                "pexels-doquyen-1520760.jpg",
                "pexels-eliezer-miranda-318040194-14992970.jpg",
                "pexels-emilygarland-1499327.jpg",
                "pexels-eyupcan-timur-424989336-33831596.jpg",
                "pexels-eyupcan-timur-424989336-33831637.jpg",
                "pexels-eyupcan-timur-424989336-33831668.jpg",
                "pexels-finn-gruber-2147533269-31869530.jpg"
        );

        if (userDao.checkData() > 0) {
            return;
        }

        for (int i = 0; i < 20; i++) {
            User user = new User();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = (lastName + "." + firstName + "@gmail.com")
                    .toLowerCase()
                    .replace(" ", "");
            String slug = (lastName + "-" + firstName)
                    .toLowerCase()
                    .replaceAll("[^a-z0-9]+", "-")
                    .replaceAll("^-|-$", "") +
                    "-" + UUID.randomUUID().toString().substring(0, 8);
            user.setId(null);
            user.setUsername(email);
            user.setSlug(slug);
            user.setPassword(passwordEncoder.encode(faker.text().text(
                    8,
                    12,
                    true,
                    true)));
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setImage(LST_IMG.get(i));
            user.setPhone(faker.phoneNumber().phoneNumber());
            user.setCredit(faker.number().numberBetween(1000, 3000));
            user.setAdmin(false);
            user.setCreateAt(LocalDateTime.now().minusDays(random.nextInt(30)));

            userDao.save(user);
        }

        User user = new User();
        user.setId(null);
        user.setUsername(environment.getProperty("ADMIN_USERNAME"));
        user.setPassword(passwordEncoder.encode(environment.getProperty("ADMIN_PASSWORD")));
        user.setSlug("doe-john" + UUID.randomUUID().toString().substring(0, 8));
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setImage("pexels-joaojesusdesign-1080213.jpg");
        user.setPhone(faker.phoneNumber().phoneNumber());
        user.setCredit(faker.number().numberBetween(3000, 5000));
        user.setAdmin(true);

        userDao.save(user);
    }
}
