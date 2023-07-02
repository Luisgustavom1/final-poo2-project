package com.freewaygpt.game.infra;

import com.freewaygpt.game.entity.Score;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ScoreDAO {
    private static EntityManagerFactory entityManagerFactory;

    public static void initialize() {
        entityManagerFactory = Persistence.createEntityManagerFactory("ScorePersistenceUnit");
    }

    public static void addDefaultScore() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Score score = new Score();
            entityManager.persist(score);

            entityTransaction.commit();
        }
        catch (Exception exception) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            exception.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }

    public static int getCurrentScore() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Score score = entityManager.createQuery("SELECT s FROM Score s", Score.class).getSingleResult();

            if(!(score == null)){
                return score.getScore();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return 0; // Valor padrão sem registro
    }

    public static void incrementScore() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Score score = entityManager.createQuery("SELECT s FROM Score s", Score.class).getSingleResult();

            if (!(score == null)) {
                score.setScore(score.getScore() + 1);
                entityManager.merge(score);
            }

            entityTransaction.commit();
        }
        catch (Exception exception) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            exception.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }

    public static void resetScore() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Score score = entityManager.createQuery("SELECT s FROM Score s", Score.class).getSingleResult();

            if (!(score == null)) {
                score.setScore(0);
                entityManager.merge(score);
            }

            entityTransaction.commit();
        }
        catch (Exception exception) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            exception.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
