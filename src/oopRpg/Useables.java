package oopRpg;
// interface useables defines methods that objects which can be used by a player require
public interface Useables {
    // all useables can be used and require a user
    public void use(Character user) throws Exception;
}
