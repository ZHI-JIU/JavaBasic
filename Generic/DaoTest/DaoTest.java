package Generic.DaoTest;

import org.junit.Test;

import java.util.List;

public class DaoTest {
    @Test
    public void test01() {
        Dao<User> dao = new Dao<>();
        User user1 = new User(1, 10, "Tom");
        User user2 = new User(2, 15, "Harry");
        User user3 = new User(3, 17, "Jimmy");
        dao.save("1", user1);
        try {
            System.out.println(dao.get("1"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        dao.save("2", user2);
        dao.update("2", user3);
        List<User> users = dao.list();
        System.out.println(users);

        try {
            dao.delete("1");
            dao.delete("4");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        users = dao.list();
        System.out.println(users);
    }
}
