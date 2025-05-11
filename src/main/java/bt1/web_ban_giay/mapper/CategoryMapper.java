package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.CategoryDTO;
import bt1.web_ban_giay.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(target = "parentId", source = "parent.id")
    CategoryDTO toDTO(Category category);
}