package com.punittewani.blogapis.blogapis.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "app_user")
public class User implements UserDetails {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) 
   private int id;
   @Column(nullable = false)
   private String name;

   @Column(nullable = false,unique = true)
   private String email;

   @Column(nullable = false)
   private String password;

   @Column(nullable = true )
   private String about;

   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL , fetch = FetchType.EAGER)
   private List<Posts> posts = new ArrayList<>();

   @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinTable(name = "user_role",
   joinColumns=@JoinColumn(name="user",referencedColumnName = "id"),
   inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id"))
   
   private Set<Role> roles = new HashSet<>();

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      List<SimpleGrantedAuthority> authorities=  this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).toList();
      return authorities;
   }

   @Override
   public String getUsername() {
     return this.email;
   }

   @Override
   public boolean isAccountNonExpired() {
      // TODO Auto-generated method stub
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return true;
   
   }

   @Override
   public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return true;
   }

   @Override
   public boolean isEnabled() {
      // TODO Auto-generated method stub
      return true;
   }
}
