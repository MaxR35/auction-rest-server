package com.rougeux.projet.auction.fixtures;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.bo.Item;
import com.rougeux.projet.auction.dal.ICategoryDao;
import com.rougeux.projet.auction.dal.IItemDao;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
public class ItemDataInitializer implements CommandLineRunner {

    private final Faker faker = new Faker();
    private final ICategoryDao categoryDao;
    private final IItemDao itemDao;

    public ItemDataInitializer(ICategoryDao categoryDao, IItemDao itemDao) {
        this.categoryDao = categoryDao;
        this.itemDao = itemDao;
    }

    private static final List<String> BRANDS = List.of(
            "Moog", "Korg", "Roland", "Yamaha", "Sequential", "Arturia",
            "Nord", "Behringer", "Elektron", "Novation", "Waldorf",
            "Oberheim", "Dreadbox", "ASM", "Access", "Dave Smith Instruments",
            "Alesis", "Casio", "M-Audio", "Kurzweil", "UDO", "Crumar",
            "Akai", "E-mu", "Ensoniq", "Vermona", "Polyend", "Modal Electronics",
            "Doepfer", "Pittsburgh Modular", "Intellijel", "Make Noise",
            "Mutable Instruments", "Studio Electronics", "Radikal Technologies",
            "GForce", "Cherry Audio", "Native Instruments", "Artisan", "Black Corporation"
    );
    private static final List<String> MODELS = List.of(
            "Minimoog Model D", "PolySix", "Juno-6", "Juno-60", "Juno-106", "Jupiter-8",
            "Jupiter-4", "Prophet-5", "Prophet-6", "OB-Xa", "OB-8", "Memorymoog", "Polymoog",
            "CS-80", "DX7", "SY77", "M1", "Mono/Poly", "MS-20", "ARP Odyssey", "ARP 2600",
            "SH-101", "TB-303", "TR-808", "TR-909", "AX60", "AX80", "DW-8000",
            "Sub 37", "Grandmother", "Matriarch", "Minitaur", "MicroBrute", "MiniBrute 2S",
            "MatrixBrute", "PolyBrute", "DeepMind 12", "Prologue 16", "Minilogue XD", "Monologue",
            "Take 5", "Pro 3", "OB-X8", "Hydrasynth", "Argon8", "Cobalt8", "Peak", "Summit",
            "Digitone", "Digitakt", "Syntakt", "Analog Four", "Analog Rytm", "Modwave", "Opsix"
    );


    @Override
    public void run(String... args) throws Exception {
        if(itemDao.checkData() > 0) {
            return;
        }
        List<Category> categories = categoryDao.findAll();

        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            String name = BRANDS.get(faker.random().nextInt(BRANDS.size())) + " " + MODELS.get(i);

            item.setId(null);
            item.setName(name);
            item.setDescription(faker.lorem().paragraph());
            item.setCategory(categories.get(faker.random().nextInt(categories.size())));

            itemDao.save(item);
        }
    }
}
