/*
 * SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.api.server.authentication;

import com.google.common.base.Strings;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIdentityTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void create_user() throws Exception {
    UserIdentity underTest = UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("1234")
      .setName("John")
      .setEmail("john@email.com")
      .build();

    assertThat(underTest.getProviderLogin()).isEqualTo("john");
    assertThat(underTest.getLogin()).isEqualTo("1234");
    assertThat(underTest.getName()).isEqualTo("John");
    assertThat(underTest.getEmail()).isEqualTo("john@email.com");
  }

  @Test
  public void create_user_without_email() throws Exception {
    UserIdentity underTest = UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("1234")
      .setName("John")
      .build();

    assertThat(underTest.getEmail()).isNull();
  }

  @Test
  public void fail_when_login_is_empty() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User login must not be blank");
    UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("")
      .setName("John")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_login_is_too_long() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User login size is incorrect (Between 3 and 255 characters)");
    UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin(Strings.repeat("1", 256))
      .setName("John")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_login_is_too_small() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User login size is incorrect (Between 3 and 255 characters)");
    UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("12")
      .setName("John")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_provider_login_is_null() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Provider login must not be blank");
    UserIdentity.builder()
      .setLogin("1234")
      .setName("John")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_provider_login_is_empty() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Provider login must not be blank");
    UserIdentity.builder()
      .setProviderLogin("")
      .setLogin("1234")
      .setName("John")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_provider_login_is_too_long() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Provider login size is incorrect (maximum 255 characters)");
    UserIdentity.builder()
      .setProviderLogin(Strings.repeat("1", 256))
      .setLogin("1234")
      .setName("John")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_name_is_null() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User name must not be blank");
    UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("1234")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_name_is_empty() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User name must not be blank");
    UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("1234")
      .setName("")
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_name_is_loo_long() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User name size is too big (200 characters max)");
    UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("1234")
      .setName(Strings.repeat("1", 201))
      .setEmail("john@email.com")
      .build();
  }

  @Test
  public void fail_when_email_is_loo_long() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User email size is too big (100 characters max)");
    UserIdentity.builder()
      .setProviderLogin("john")
      .setLogin("1234")
      .setName("John")
      .setEmail(Strings.repeat("1", 101))
      .build();
  }
}
