package com.api.todolist.application.controller;

import com.api.todolist.TodoListApplication;
import com.api.todolist.infrastructure.entity.TaskEntity;
import com.api.todolist.infrastructure.entity.UserEntity;
import com.api.todolist.infrastructure.repository.SpringDataStatusRepository;
import com.api.todolist.infrastructure.repository.SpringDataTaskRepository;
import com.api.todolist.infrastructure.repository.SpringDataUserRepository;
import lombok.Getter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TodoListApplication.class, TodoListController.class})
class TodoListControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Getter
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @MockBean
    private SpringDataUserRepository springDataUserRepository;

    @MockBean
    private SpringDataTaskRepository springDataTaskRepository;

    @MockBean
    private SpringDataStatusRepository springDataStatusRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeAll
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private UserEntity userEntity = new UserEntity();

    @BeforeEach
    void setup() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(new User("Y1M96LLiAwIJ5q8WFuvl", "i63aw0t39WIQZAXKsmW3zEN8A", new ArrayList<>()));

        SecurityContextHolder.setContext(securityContext);

        userEntity.setClientId("Y1M96LLiAwIJ5q8WFuvl");
        userEntity.setClientSecret("i63aw0t39WIQZAXKsmW3zEN8A");
        userEntity.setName("Obi-Wan Kenoby Test");
    }

    @Test
    void givenRequestGenerateTokenWhenClientCredentialsOkThenReturnSucessToken() throws Exception {
        //given
        final var request = "{\"clientId\":\"Y1M96LLiAwIJ5q8WFuvl\",\"clientSecret\":\"i63aw0t39WIQZAXKsmW3zEN8A\"}";

        //when
        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(new UsernamePasswordAuthenticationToken("", ""));
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));

        //then
        getMockMvc().perform(post("/api/v1/auth/oauth/token-jwt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestGenerateTokenWhenClientCredentialsNOkThenReturnNotFound() throws Exception {
        //given
        final var request = "{\"clientId\":\"Y1M96LLiAwIJ5q8WFuvl\",\"clientSecret\":\"i63aw0t39WIQZAXKsmW3zEN8A\"}";

        //when
        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(new UsernamePasswordAuthenticationToken("", ""));
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.empty());

        //then
        getMockMvc().perform(post("/api/v1/auth/oauth/token-jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenRequestListTaskWhenStatusPendingAndSuperUserThenReturnOk() throws Exception {
        //given
        userEntity.setFlAdmin(true);

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(springDataTaskRepository.findAll(Mockito.any(Sort.class))).thenReturn(List.of(MockEntity.mockTaskEntityStatusPending()));

        //then
        getMockMvc().perform(get("/api/v1/task")
                        .queryParam("status", "PENDING"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestListTaskWhenStatusCompletedAndSuperUserThenReturnOk() throws Exception {
        //given
        userEntity.setFlAdmin(true);

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(springDataTaskRepository.findAll(Mockito.any(Sort.class))).thenReturn(List.of(MockEntity.mockTaskEntityStatusCompleted()));

        //then
        getMockMvc().perform(get("/api/v1/task")
                        .queryParam("status", "COMPLETED"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestListTaskWhenStatusNoneAndSuperUserThenReturnOk() throws Exception {
        //given
        userEntity.setFlAdmin(true);

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(springDataTaskRepository.findAll(Mockito.any(Sort.class))).thenReturn(List.of(MockEntity.mockTaskEntityStatusPending()));

        //then
        getMockMvc().perform(get("/api/v1/task"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestListTaskWhenStatusNoneAndNormalUserThenReturnOk() throws Exception {
        //given
        userEntity.setFlAdmin(false);

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(springDataTaskRepository.findByUser(Mockito.any(UserEntity.class), Mockito.any(Sort.class))).thenReturn(List.of(MockEntity.mockTaskEntityStatusPending()));

        //then
        getMockMvc().perform(get("/api/v1/task"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestListTaskWhenStatusNoneAndNormalUserThenReturnNotFound() throws Exception {
        //given
        userEntity.setFlAdmin(false);

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(springDataTaskRepository.findByUser(Mockito.any(UserEntity.class), Mockito.any(Sort.class))).thenReturn(new ArrayList<>());

        //then
        getMockMvc().perform(get("/api/v1/task"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenRequestPostWhenSaveTaskThenReturnOk() throws Exception {
        //given
        final var request = "{\"name\":\"Darth Vader\",\"description\":\"Darth Vader is father Luke and Lea\",\"status\":\"PENDING\"}";

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(springDataStatusRepository.findByDescription(Mockito.anyString())).thenReturn(Optional.of(MockEntity.mockStatusEntity()));
        Mockito.when(springDataTaskRepository.save(Mockito.any(TaskEntity.class))).thenReturn(MockEntity.mockTaskEntityStatusPending());

        //then
        getMockMvc().perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestPostWhenSaveTaskButUserNotFoundThenReturnNotFound() throws Exception {
        //given
        final var request = "{\"name\":\"Darth Vader\",\"description\":\"Darth Vader is father Luke and Lea\",\"status\":\"PENDING\"}";

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.empty());

        //then
        getMockMvc().perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenRequestPostWhenSaveTaskButStatusNotFoundThenReturnNotFound() throws Exception {
        //given
        final var request = "{\"name\":\"Darth Vader\",\"description\":\"Darth Vader is father Luke and Lea\",\"status\":\"PENDING\"}";

        //when
        Mockito.when(springDataUserRepository.findByClientId(Mockito.anyString())).thenReturn(Optional.of(userEntity));
        Mockito.when(springDataStatusRepository.findByDescription(Mockito.anyString())).thenReturn(Optional.empty());

        //then
        getMockMvc().perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenRequestPutWhenUpdateTaskThenReturnOk() throws Exception {
        //given
        final var id = 1L;
        final var request = "{\"name\":\"Darth Vader\",\"description\":\"Darth Vader is father Luke and Lea\",\"status\":\"PENDING\"}";

        //when
        Mockito.when(springDataTaskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(MockEntity.mockTaskEntityStatusPending()));
        Mockito.when(springDataStatusRepository.findByDescription(Mockito.anyString())).thenReturn(Optional.of(MockEntity.mockStatusEntity()));
        Mockito.when(springDataTaskRepository.save(Mockito.any(TaskEntity.class))).thenReturn(MockEntity.mockTaskEntityStatusPending());

        //then
        getMockMvc().perform(delete("/api/v1/task/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestPutWhenUpdateTaskButTaskNotFoundThenReturnNotFound() throws Exception {
        //given
        final var id = 1L;
        final var request = "{\"name\":\"Darth Vader\",\"description\":\"Darth Vader is father Luke and Lea\",\"status\":\"PENDING\"}";

        //when
        Mockito.when(springDataTaskRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //then
        getMockMvc().perform(put("/api/v1/task/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenRequestPutWhenUpdateTaskButStatusNotFoundThenReturnNotFound() throws Exception {
        //given
        final var id = 1L;
        final var request = "{\"name\":\"Darth Vader\",\"description\":\"Darth Vader is father Luke and Lea\",\"status\":\"PENDING\"}";

        //when
        Mockito.when(springDataTaskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(MockEntity.mockTaskEntityStatusPending()));
        Mockito.when(springDataStatusRepository.findByDescription(Mockito.anyString())).thenReturn(Optional.empty());

        //then
        getMockMvc().perform(put("/api/v1/task/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenRequestDeleteWhenDeleteTaskThenReturnOk() throws Exception {
        //given
        final var id = 1L;

        //when
        Mockito.when(springDataTaskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(MockEntity.mockTaskEntityStatusPending()));
        Mockito.doNothing().when(springDataTaskRepository).delete(Mockito.any(TaskEntity.class));

        //then
        getMockMvc().perform(delete("/api/v1/task/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenRequestDeleteWhenDeleteTaskButTaskNotFoundThenReturnNotFound() throws Exception {
        //given
        final var id = 1L;

        //when
        Mockito.when(springDataTaskRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //then
        getMockMvc().perform(delete("/api/v1/task/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}