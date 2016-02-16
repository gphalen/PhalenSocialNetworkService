package com.sn.dblayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.sn.vo.Message;
import com.sn.vo.Subscription;
import com.sn.vo.User;

public class DBLayer {
	
	public static User getUser(String username, String password) throws SQLException {
		String query = "select * from user where username='" + username + "' and password='" + password + "'";
		System.out.println(query);
	 	ResultSet rs = DbHelper.executeQuery(query);
	 	User user = null;
	 	
	 	if (rs.next()) {
	 		user = new User();
	 		user.setId(rs.getInt("id"));
	 		user.setUsername(rs.getString("username"));
	 		user.setName(rs.getString("name"));
	 		user.setAddress(rs.getString("address"));
	 		user.setPhone(rs.getString("phone"));
	 		user.setEmail(rs.getString("email"));
	 		user.setAge(rs.getInt("age"));
	 		
	 		String query2 = "select * from subscription where subscriber_id=" + user.getId();
	 		ResultSet rs2 = DbHelper.executeQuery(query2);
	 		
	 		while (rs2.next()) {
	 			Subscription subscription = new Subscription();
	 			subscription.setSubscriberId(rs2.getInt("subscriber_id"));
	 			subscription.setSubscribedId(rs2.getInt("subscribed_id"));
	 			user.getSubscriptions().add(subscription);
	 		}
	 	}
		
	 	return user;
	}
	
	public static User getUser(int userid) throws SQLException {
		String query = "select * from user where id=" + userid;
		System.out.println(query);
	 	ResultSet rs = DbHelper.executeQuery(query);
	 	User user = null;
	 	
	 	if (rs.next()) {
	 		user = new User();
	 		user.setId(rs.getInt("id"));
	 		user.setUsername(rs.getString("username"));
	 		user.setName(rs.getString("name"));
	 		user.setAddress(rs.getString("address"));
	 		user.setPhone(rs.getString("phone"));
	 		user.setEmail(rs.getString("email"));
	 		user.setAge(rs.getInt("age"));
	 		
	 		String query2 = "select * from subscription where subscriber_id=" + user.getId();
	 		ResultSet rs2 = DbHelper.executeQuery(query2);
	 		
	 		while (rs2.next()) {
	 			Subscription subscription = new Subscription();
	 			subscription.setSubscriberId(rs2.getInt("subscriber_id"));
	 			subscription.setSubscribedId(rs2.getInt("subscribed_id"));
	 			user.getSubscriptions().add(subscription);
	 		}
	 	}
		
	 	return user;
	}
	
	public static void registerUser(User user) throws Exception {
		String query = "insert into user(username,name,address,phone,email,age,password)" 
						+ " values('" + user.getUsername() + "', '" + user.getName() + "', '" + user.getAddress() 
						+ "', '" + user.getPhone() + "', '" + user.getEmail() + "'," + user.getAge() 
						+ ", '" + user.getPassword() + "')";
	 	int newUserId = DbHelper.executeUpdateAndReturnGeneratedKey(query);
	 	
	 	user.setId(newUserId);
	}
	
	public static void updateUser(User user) throws Exception {
		String query = "update user set username='" + user.getUsername() + "', name='" + user.getName() 
					   + "',address='" + user.getAddress() + "',phone='" + user.getPhone() + "',email='" 
					   + user.getEmail() + "',age=" + user.getAge() + " where id=" + user.getId();
	 	DbHelper.executeQuery(query);
	}
	
	public static List<User> getAllUsers() throws SQLException {
		
		List<User> users = new ArrayList<User>();
		String query = "select * from users";
	 	ResultSet rs = DbHelper.executeQuery(query);
	 	
	 	while (rs.next()) {
	 		User user = new User();
	 		user.setId(rs.getInt("id"));
	 		user.setUsername(rs.getString("username"));
	 		user.setName(rs.getString("name"));
	 		user.setAddress(rs.getString("address"));
	 		user.setPhone(rs.getString("phone"));
	 		user.setEmail(rs.getString("email"));
	 		user.setAge(rs.getInt("age"));
	 		users.add(user);
	 	}
		return users;
	}
	
	public static List<User> searchUsers(String name) throws SQLException {
		
		List<User> users = new ArrayList<User>();
		String query = "select * from user where name like '%" + name + "%'";
	 	ResultSet rs = DbHelper.executeQuery(query);
	 	
	 	while (rs.next()) {
	 		User user = new User();
	 		user.setId(rs.getInt("id"));
	 		user.setUsername(rs.getString("username"));
	 		user.setName(rs.getString("name"));
	 		user.setAddress(rs.getString("address"));
	 		user.setPhone(rs.getString("phone"));
	 		user.setEmail(rs.getString("email"));
	 		user.setAge(rs.getInt("age"));
	 		users.add(user);
	 	}
		return users;
	}

	public static void subscribe(int subscriber_id, int subscribed_id) throws Exception {
		String query = "insert into subscription(subscriber_id, subscribed_id)"
				+ " values(" + subscriber_id + ", " + subscribed_id + ")";
		System.out.println(query);
		int res = DbHelper.executeUpdate(query);
		if (res != 1) {
	 		throw new Exception("Failed to subscribe.");
	 	}
	}
	
	public static void sendMessage(Message msg) throws Exception {
		String query = "insert into message(sender_id, text, receiver_type)"
				+ " values (" + msg.getSender().getId() + ", '" + msg.getText() + "', " + msg.getReceiverType()
				+ ")";
		System.out.println(query);
		int res = DbHelper.executeUpdateAndReturnGeneratedKey(query);
		msg.setId(res);
	}
	
	public static List<Message> getAllPublicMessages() throws SQLException {
		
		String query = "select * from message where receiver_type=1 order by sending_time desc";
		List<Message> messages = new ArrayList<Message>();
		ResultSet rs = DbHelper.executeQuery(query);
		
		while (rs.next()) {
			Message msg = new Message();
			msg.setId(rs.getInt("id"));
			msg.setReceiverType(rs.getInt("receiver_type"));
			msg.setSendingTime(rs.getTimestamp("sending_time"));
			msg.setText(rs.getString("text"));
			msg.setSender(getUser(rs.getInt("sender_id")));
			messages.add(msg);
		}
		return messages;
	}
	
	public static List<Message> getInboxMessages(int userid) throws SQLException {
		
		String query = "select * from subscription join message on subscription.subscribed_id=message.sender_id "
					+ " where receiver_type=2 order by sending_time desc";
		System.out.println(query);
		List<Message> messages = new ArrayList<Message>();
		ResultSet rs = DbHelper.executeQuery(query);
		
		while (rs.next()) {
			Message msg = new Message();
			msg.setId(rs.getInt("message.id"));
			msg.setReceiverType(rs.getInt("receiver_type"));
			msg.setSendingTime(rs.getDate("sending_time"));
			msg.setText(rs.getString("text"));
			msg.setSender(getUser(rs.getInt("sender_id")));
			messages.add(msg);
		}
		
		messages.addAll(getAllPublicMessages());
		messages.sort(new Comparator<Message>() {

			@Override
			public int compare(Message m1, Message m2) {
				if (m1.getSendingTime().before(m2.getSendingTime())) {
					return 1;
				}
				else if (m1.getSendingTime().after(m2.getSendingTime())) {
					return -1;
				}
				else {
					return 0;
				}
			}
		});
		
		return messages;
	}
}
