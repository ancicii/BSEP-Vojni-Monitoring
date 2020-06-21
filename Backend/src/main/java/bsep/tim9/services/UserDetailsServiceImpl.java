package bsep.tim9.services;

import bsep.tim9.model.User;
import bsep.tim9.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  public Map<String,Integer> userCheck = new HashMap<String, Integer>();
  public Map<String,Date> userWatch = new HashMap<String, Date>();


  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
    } else {
    	return user;
    }
  }

  public void increaseValue(String email){

    if(!userCheck.containsKey(email)){
      userCheck.put(email,1);
    }else{
      userCheck.put(email,userCheck.get(email) + 1);
    }

    if(userCheck.get(email) >= 3){
      Date date = new Date();
      date.setMinutes(date.getMinutes() + 2);
      userWatch.put(email,date);
    }

  }

  public boolean checkTimer(String email){
    if(userWatch.containsKey(email)) {
      System.out.println("dada");
      Date d = new Date();
      if(userWatch.get(email).getTime() < d.getTime()){
        userCheck.remove(email);
        userWatch.remove(email);
        return true;
      }else
        return false;

    }
    return true;
  }


}
