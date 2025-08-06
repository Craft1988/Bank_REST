package com.example.bankcards.controller;

import com.example.bankcards.TestDataBuilder;
import com.example.bankcards.dto.card.CreateCardRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "Ivan", roles = "ADMIN")
public class AdminControllerTest extends BaseControllerTest {

    private static final String BASE_URI = "/admin/cards";

    @Test
    @DisplayName("POST /admin/cards - 400 при некорректном запросе")
    void createCard_validationError() throws Exception {
        // пустой JSON приведёт к ошибке @Valid
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("POST /admin/cards - 403 для не-ADMIN")
    @WithMockUser(roles = "USER")
    void createCard_forbidden() throws Exception {
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /admin/cards - успешно создаёт карту")
    @WithMockUser(roles = "ADMIN")
    void createCard_success() throws Exception {

        Mockito.when(cardService.createCard(any(CreateCardRequest.class)))
                .thenReturn(TestDataBuilder.createdCard());

        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestDataBuilder.cardRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.number").value("4321-8765-2109-6543"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.ownerUsername").value("Ivan"));
    }



}
