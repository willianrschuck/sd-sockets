package socket;



import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import model.Ad;
import model.User;

public class Database {
	
	static int adSequence = 1;

	static List<User> usuarios;
	static List<Ad> ads;
	
	static {
		usuarios = new ArrayList<>();
		usuarios.add(new User("admin", "admin"));
	}
	
	static {
		ads = new ArrayList<>();
	}

	
	private Database() {}
	
	
	public static User getUserBy(String username) {
		for (User user : usuarios) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public static void save(Ad ad) {
		ad.setId(adSequence++);
		ads.add(ad);
	}
	
	
	public static Ad getAd(Integer id) {
		for (Ad ad : ads) {
			if (ad.getId() == id) {
				return ad;
			}
		}
		return null;
	}
	
	public static void deleteAd(Ad ad) {
		ads.remove(ad);
	}
	

	public static List<Ad> searchAd(String fieldName, Object value) {
		List<Ad> resultAds = new ArrayList<Ad>();
		try {
			Method readMethod = new PropertyDescriptor(fieldName, Ad.class).getReadMethod();
			for (Ad ad : ads) {
				Object theValue = readMethod.invoke(ad);
				
				if (theValue instanceof String && value instanceof String) {
					if (((String) theValue).contains(((String) value))) {
						resultAds.add(ad);
					}
				} else {
					if (theValue.equals(value)) {
						resultAds.add(ad);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultAds;
	}
	
}
