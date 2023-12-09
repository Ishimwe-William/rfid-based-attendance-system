//package com.bunsen.rfidbasedattendancesystem.service;
//
//import com.bunsen.rfidbasedattendancesystem.model.Card;
//import com.bunsen.rfidbasedattendancesystem.model.Role;
//import com.bunsen.rfidbasedattendancesystem.model.User;
//import com.bunsen.rfidbasedattendancesystem.model.UserDto;
//import com.bunsen.rfidbasedattendancesystem.repository.CardRepository;
//import com.bunsen.rfidbasedattendancesystem.repository.RoleRepository;
//import com.bunsen.rfidbasedattendancesystem.repository.UserRepository;
//import jakarta.mail.MessagingException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserServiceImpl{
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final CardRepository cardRepository;
//    private final EmailService emailService;
//
//    public UserServiceImpl(UserRepository userRepository,
//                           RoleRepository roleRepository,
//                           PasswordEncoder passwordEncoder, CardRepository cardRepository, EmailService emailService) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.cardRepository = cardRepository;
//        this.emailService = emailService;
//    }
//
//    public void saveUser(UserDto userDto) {
//        User user = new User();
//        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
//        user.setEmail(userDto.getEmail());
//        // encrypt the password using spring security
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//
//        Role role = roleRepository.findByName("ROLE_ADMIN");
//        if(role == null){
//            role = checkRoleExist();
//        }
//        user.setRoles(Arrays.asList(role));
//        userRepository.save(user);
//    }
//
//    @Override
//    public User findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public List<UserDto> findAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map((user) -> mapToUserDto(user))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void associateUserWithCard(String cardNumber, Long userId) throws MessagingException {
//        User user = userRepository.findById(userId).orElse(null);
//        Card card = cardRepository.findByCardNumber(cardNumber);
//
//        if (user != null && card != null) {
//            user.setCard(card);
//            userRepository.save(user);
//            emailService.prepareAndSendEmail(user.getEmail(),"RBAS","Your Associate Card Number",
//                    "Card: "+cardNumber+"\n"+ "User: "+ user.getName());
//        }
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    private UserDto mapToUserDto(User user){
//        UserDto userDto = new UserDto();
//        String[] str = user.getName().split(" ");
//        userDto.setFirstName(str[0]);
//        userDto.setLastName(str[1]);
//        userDto.setEmail(user.getEmail());
//        return userDto;
//    }
//
//    private Role checkRoleExist(){
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        return roleRepository.save(role);
//    }
//
//    public User findByCardNumber(String cardNo) {
//    }
//}