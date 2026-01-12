public class Network {

    private User[] users;  
    private int userCount; 

    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) {
                return users[i];
            }
        }
        return null;
    }

    public boolean addUser(String name) {
        if (userCount >= users.length || getUser(name) != null) {
            return false;
        }
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    public boolean addFollowee(String name1, String name2) {
        User u1 = getUser(name1);
        User u2 = getUser(name2);
        if (u1 == null || u2 == null || name1.equalsIgnoreCase(name2)) {
            return false;
        }
        return u1.addFollowee(name2);
    }
    
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) return null;
        User recommendation = null;
        int maxMutuals = -1;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) {
                continue;
            }
            int mutuals = user.countMutual(users[i]);
            if (mutuals > maxMutuals) {
                maxMutuals = mutuals;
                recommendation = users[i];
            }
        }
        return (recommendation != null) ? recommendation.getName() : null;
    }

    public String mostPopularUser() {
        if (userCount == 0) return null;
        String popularName = null;
        int maxFollowers = -1;
        for (int i = 0; i < userCount; i++) {
            int currentFollowers = followeeCount(users[i].getName());
            if (currentFollowers > maxFollowers) {
                maxFollowers = currentFollowers;
                popularName = users[i].getName();
            }
        }
        return popularName;
    }

    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                count++;
            }
        }
        return count;
    }

    public String toString() {
       String ans = "Network:";
       for (int i = 0; i < userCount; i++) {
           ans += "\n" + users[i].toString();
       }
       return ans;
    }
}