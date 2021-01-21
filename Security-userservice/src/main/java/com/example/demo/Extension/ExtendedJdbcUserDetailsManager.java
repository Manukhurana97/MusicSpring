//package com.example.demo.Extension;
//
//import com.example.demo.Dao.UserDao;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserCache;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.cache.NullUserCache;
//import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
//import org.springframework.security.provisioning.GroupManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.List;
//
//@Repository
//public class ExtendedJdbcUserDetailsManager extends JdbcDaoImpl implements UserDetailsManager, GroupManager {
//
//    @Autowired
//    private UserDao dao;
//
//    public static final String DEF_CREATE_USER_SQL = "insert into users (username, password, enabled) values (?,?,?)";
//
//    public static final String DEF_DELETE_USER_SQL = "delete from users where username = ?";
//
//    public static final String DEF_UPDATE_USER_SQL = "update users set password = ?, enabled = ? where username = ?";
//
//    public static final String DEF_INSERT_AUTHORITY_SQL = "insert into authorities (username, authority) values (?,?)";
//
//    public static final String DEF_DELETE_USER_AUTHORITIES_SQL = "delete from authorities where username = ?";
//
//    public static final String DEF_USER_EXISTS_SQL = "select username from users where username = ?";
//
//    public static final String DEF_CHANGE_PASSWORD_SQL = "update users set password = ? where username = ?";
//
//    public static final String DEF_FIND_GROUPS_SQL = "select group_name from groups";
//
//    public static final String DEF_FIND_USERS_IN_GROUP_SQL = "select username from group_members gm, groups g "
//            + "where gm.group_id = g.id and g.group_name = ?";
//
//    public static final String DEF_INSERT_GROUP_SQL = "insert into groups (group_name) values (?)";
//
//    public static final String DEF_FIND_GROUP_ID_SQL = "select id from groups where group_name = ?";
//
//    public static final String DEF_INSERT_GROUP_AUTHORITY_SQL = "insert into group_authorities (group_id, authority) values (?,?)";
//
//    public static final String DEF_DELETE_GROUP_SQL = "delete from groups where id = ?";
//
//    public static final String DEF_DELETE_GROUP_AUTHORITIES_SQL = "delete from group_authorities where group_id = ?";
//
//    public static final String DEF_DELETE_GROUP_MEMBERS_SQL = "delete from group_members where group_id = ?";
//
//    public static final String DEF_RENAME_GROUP_SQL = "update groups set group_name = ? where group_name = ?";
//
//    public static final String DEF_INSERT_GROUP_MEMBER_SQL = "insert into group_members (group_id, username) values (?,?)";
//
//    public static final String DEF_DELETE_GROUP_MEMBER_SQL = "delete from group_members where group_id = ? and username = ?";
//
//    public static final String DEF_GROUP_AUTHORITIES_QUERY_SQL = "select g.id, g.group_name, ga.authority "
//            + "from groups g, group_authorities ga " + "where g.group_name = ? " + "and g.id = ga.group_id ";
//
//    public static final String DEF_DELETE_GROUP_AUTHORITY_SQL = "delete from group_authorities where group_id = ? and authority = ?";
//
//    protected final Log logger = LogFactory.getLog(getClass());
//
//    private String createUserSql = DEF_CREATE_USER_SQL;
//
//    private String deleteUserSql = DEF_DELETE_USER_SQL;
//
//    private String updateUserSql = DEF_UPDATE_USER_SQL;
//
//    private String createAuthoritySql = DEF_INSERT_AUTHORITY_SQL;
//
//    private String deleteUserAuthoritiesSql = DEF_DELETE_USER_AUTHORITIES_SQL;
//
//    private String userExistsSql = DEF_USER_EXISTS_SQL;
//
//    private String changePasswordSql = DEF_CHANGE_PASSWORD_SQL;
//
//    private String findAllGroupsSql = DEF_FIND_GROUPS_SQL;
//
//    private String findUsersInGroupSql = DEF_FIND_USERS_IN_GROUP_SQL;
//
//    private String insertGroupSql = DEF_INSERT_GROUP_SQL;
//
//    private String findGroupIdSql = DEF_FIND_GROUP_ID_SQL;
//
//    private String insertGroupAuthoritySql = DEF_INSERT_GROUP_AUTHORITY_SQL;
//
//    private String deleteGroupSql = DEF_DELETE_GROUP_SQL;
//
//    private String deleteGroupAuthoritiesSql = DEF_DELETE_GROUP_AUTHORITIES_SQL;
//
//    private String deleteGroupMembersSql = DEF_DELETE_GROUP_MEMBERS_SQL;
//
//    private String renameGroupSql = DEF_RENAME_GROUP_SQL;
//
//    private String insertGroupMemberSql = DEF_INSERT_GROUP_MEMBER_SQL;
//
//    private String deleteGroupMemberSql = DEF_DELETE_GROUP_MEMBER_SQL;
//
//    private String groupAuthoritiesSql = DEF_GROUP_AUTHORITIES_QUERY_SQL;
//
//    private String deleteGroupAuthoritySql = DEF_DELETE_GROUP_AUTHORITY_SQL;
//
//    private AuthenticationManager authenticationManager;
//
//    private UserCache userCache = new NullUserCache();
//
//    public ExtendedJdbcUserDetailsManager() {
//    }
//
//    public ExtendedJdbcUserDetailsManager(DataSource dataSource) {
//        setDataSource(dataSource);
//    }
//
//    @Override
//    public void createUser(UserDetails user) {
//        System.out.println(user.getUsername());
//    }
//
//    @Override
//    public void updateUser(UserDetails user) {
//
//    }
//
//    @Override
//    public void deleteUser(String username) {
//
//    }
//
//    @Override
//    public void changePassword(String oldPassword, String newPassword) {
//
//    }
//
//
//    @Override
//    public boolean userExists(String username) {
//        return false;
//    }
//
//
//
//    @Override
//    public List<String> findAllGroups() {
//        return null;
//    }
//
//    @Override
//    public List<String> findUsersInGroup(String groupName) {
//        return null;
//    }
//
//    @Override
//    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
//
//    }
//
//    @Override
//    public void deleteGroup(String groupName) {
//
//    }
//
//    @Override
//    public void renameGroup(String oldName, String newName) {
//
//    }
//
//    @Override
//    public void addUserToGroup(String username, String group) {
//
//    }
//
//    @Override
//    public void removeUserFromGroup(String username, String groupName) {
//
//    }
//
//    @Override
//    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
//        return null;
//    }
//
//    @Override
//    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
//
//    }
//
//    @Override
//    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
//
//    }
//
//
//}
