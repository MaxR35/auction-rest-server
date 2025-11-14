package com.rougeux.projet.auction.fixtures;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.dal.ICategoryDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Order(2)
@Component
public class CategoryDataInitializer implements CommandLineRunner {

    private final ICategoryDao categoryDao;

    public CategoryDataInitializer(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    private static final List<String> LABELS = List.of(
            "Analog",
            "Digital",
            "Modular / Semi-modular",
            "Hardware virtual / Hybrid",
            "Monophonic",
            "Polyphonic"
    );

    @Override
    public void run(String... args) throws Exception {
        if(categoryDao.checkData() > 0) {
            return;
        }

        for (String label : LABELS) {

            String slug = label
                    .toLowerCase()
                    .replaceAll("[^a-z0-9\\s-]", "-")
                    .replaceAll("\\s+", "-")
                    .replaceAll("-{2,}", "-")
                    .replaceAll("^-|-$", "")
                    + "-" + UUID.randomUUID().toString().substring(0, 8);
            Category category = new Category();
            category.setId(null);
            category.setSlug(slug);
            category.setLabel(label);

            categoryDao.save(category);
        }
    }
}
